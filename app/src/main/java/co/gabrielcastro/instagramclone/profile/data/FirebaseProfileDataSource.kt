package co.gabrielcastro.instagramclone.profile.data

import co.gabrielcastro.instagramclone.common.base.RequestCallback
import co.gabrielcastro.instagramclone.common.model.Post
import co.gabrielcastro.instagramclone.common.model.User
import co.gabrielcastro.instagramclone.common.model.UserAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import java.lang.RuntimeException

class FirebaseProfileDataSource : ProfileDataSource {
  override fun fetchUserProfile(
    userUUID: String,
    callback: RequestCallback<Pair<User, Boolean?>>
  ) {
    FirebaseFirestore.getInstance()
      .collection("/users")
      .document(userUUID)
      .get()
      .addOnSuccessListener { res ->
        when(val user = res.toObject(User::class.java)) {
          null -> {
            callback.onFailure("Fail when convert user")
          }
          else -> {
            if (user.uuid == FirebaseAuth.getInstance().uid) {
              callback.onSuccess(Pair(user, null))
            } else {
              FirebaseFirestore.getInstance()
                .collection("/followers")
                .document(userUUID)
                .get()
                .addOnSuccessListener { response ->
                  if (!response.exists()) {
                    callback.onSuccess(Pair(user, false))
                  }else {
                    val list = response.get("followers") as List<String>
                    callback.onSuccess(Pair(user, list.contains(FirebaseAuth.getInstance().uid)))
                  }
                }
                .addOnFailureListener { exception ->
                  callback.onFailure(exception.message ?: "Falha ao buscar server")
                }
                .addOnCompleteListener {
                  callback.onComplete()
                }
            }
          }
        }
      }
      .addOnFailureListener { exception ->
        callback.onFailure(exception.message ?: "")
      }
      .addOnCompleteListener {
        callback.onComplete()
      }
  }

  override fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>) {
    FirebaseFirestore.getInstance()
      .collection("posts")
      .document(userUUID)
      .collection("posts")
      .orderBy("timestamp", Query.Direction.DESCENDING)
      .get()
      .addOnSuccessListener { res ->
        val documents = res.documents
        val posts = mutableListOf<Post>()
        for (document in documents) {
          val post = document.toObject(Post::class.java)
          post?.let {
            posts.add(it)
          }
        }
        callback.onSuccess(posts)
      }
      .addOnFailureListener { exception ->
        callback.onFailure(exception.message ?: "Falha ao buscar folhas")
      }
      .addOnCompleteListener {
        callback.onComplete()
      }
  }

  override fun followUser(uuid: String, follow: Boolean, callback: RequestCallback<Boolean>) {
    val uid = FirebaseAuth.getInstance().uid ?: throw RuntimeException("User not logged")
    FirebaseFirestore.getInstance()
      .collection("/followers")
      .document(uuid)
      .update("followers", if (follow) FieldValue.arrayUnion(uid) else FieldValue.arrayRemove(uid))
      .addOnSuccessListener { res ->
        followingCounter(uid, follow)
        followersCounter(uuid, callback)

        updateFeed(uuid, follow)
      }
      .addOnFailureListener { exc ->
        val err = exc as? FirebaseFirestoreException
        if (err?.code == FirebaseFirestoreException.Code.NOT_FOUND) {
          FirebaseFirestore.getInstance()
            .collection("/followers")
            .document(uuid)
            .set(
              hashMapOf(
                "followers" to listOf(uid)
              )
            )
            .addOnSuccessListener { resExc ->
              followingCounter(uid, follow)
              followersCounter(uuid, callback)

              updateFeed(uuid, follow)
            }
            .addOnFailureListener { excExc ->
              callback.onFailure(exc.message ?: "Falha ao criar seguidor")
            }
        }
        callback.onFailure(exc.message ?: "Falha ao atualizar seguidor")
      }
      .addOnCompleteListener {
        callback.onComplete()
      }
  }

  private fun updateFeed(userUUID: String, follow: Boolean) {
    if (!follow) {
      // remove from feed
      FirebaseFirestore.getInstance()
        .collection("/feeds")
        .document(FirebaseAuth.getInstance().uid!!)
        .collection("posts")
        .whereEqualTo("publisher.uuid", userUUID)
        .get()
        .addOnSuccessListener { res ->
          val documents = res.documents
          for (document in documents) {
            document.reference.delete()
          }
        }
        .addOnFailureListener { exc ->

        }
    } else {
      // add on feed
      FirebaseFirestore.getInstance()
        .collection("/posts")
        .document(userUUID)
        .collection("posts")
        .get()
        .addOnSuccessListener { res ->
          val posts = res.toObjects(Post::class.java)
          posts.lastOrNull()?.let {
            FirebaseFirestore.getInstance()
              .collection("/feeds")
              .document(FirebaseAuth.getInstance().uid!!)
              .collection("posts")
              .document(it.uuid!!)
              .set(it)
          }
        }
    }
  }

  private fun followingCounter(uid: String, follow: Boolean) {
    val meRef = FirebaseFirestore.getInstance()
      .collection("/users")
      .document(uid)

    if (follow) meRef.update("following", FieldValue.increment(1))
    else meRef.update("following", FieldValue.increment(-1))
  }

  private fun followersCounter(uid: String, callback: RequestCallback<Boolean>) {
    val meRef = FirebaseFirestore.getInstance()
      .collection("/users")
      .document(uid)

    FirebaseFirestore.getInstance()
      .collection("/followers")
      .document(uid)
      .get()
      .addOnSuccessListener { res ->
        if (res.exists()) {
          val list = res.get("followers") as List<String>
          meRef.update("followers", list.size)
        }
        callback.onSuccess(true)
      }
  }

}