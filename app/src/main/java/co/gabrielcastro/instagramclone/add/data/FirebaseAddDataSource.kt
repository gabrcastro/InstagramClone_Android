package co.gabrielcastro.instagramclone.add.data

import android.net.Uri
import co.gabrielcastro.instagramclone.common.base.RequestCallback
import co.gabrielcastro.instagramclone.common.model.Post
import co.gabrielcastro.instagramclone.common.model.User
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import java.lang.RuntimeException

class FirebaseAddDataSource : AddDataSource {
  override fun createPost(
    userUUID: String,
    uri: Uri,
    caption: String,
    callback: RequestCallback<Boolean>
  ) {

    val uriLastPath = uri.lastPathSegment ?: throw IllegalArgumentException("Invalid image")
    val imgRef = FirebaseStorage.getInstance().reference
      .child("images/")
      .child(userUUID)
      .child(uriLastPath)
    imgRef.putFile(uri)
      .addOnSuccessListener { res ->
        imgRef.downloadUrl
          .addOnSuccessListener { resDownload ->
            val meRef = FirebaseFirestore.getInstance()
              .collection("/users")
              .document(userUUID)

              meRef.get()
              .addOnSuccessListener { resMe ->
                val me = resMe.toObject(User::class.java)
                val postRef = FirebaseFirestore.getInstance().collection("/posts")
                  .document(userUUID)
                  .collection("posts")
                  .document()
                val post = Post(
                  uuid = postRef.id,
                  url = resDownload.toString(),
                  caption = caption,
                  timestamp = System.currentTimeMillis(),
                  publisher = me
                )

                postRef.set(post)
                  .addOnSuccessListener {resPost ->

                    meRef.update("postCount", FieldValue.increment(1))

                    //MyFeed
                    FirebaseFirestore.getInstance()
                      .collection("/feeds")
                      .document(userUUID)
                      .collection("posts")
                      .document(postRef.id)
                      .set(post)
                      .addOnSuccessListener { resMyFeed ->

                        //Feed my followers
                        FirebaseFirestore.getInstance()
                          .collection("/followers")
                          .document(userUUID)
                          .get()
                          .addOnSuccessListener { resFeedMyFollowers ->
                            if (resFeedMyFollowers.exists()) {
                              val list = resFeedMyFollowers.get("followers") as List<String>
                              for (followerUUID in list) {
                                FirebaseFirestore.getInstance()
                                  .collection("/feeds")
                                  .document(followerUUID)
                                  .collection("posts")
                                  .document(postRef.id)
                                  .set(post)
                              }
                            }
                            callback.onSuccess(true)
                          }
                          .addOnFailureListener { excFeedMyFollowers->
                            callback.onFailure(excFeedMyFollowers.message ?: "Falha ao buscar meus seguidores")
                          }
                          .addOnCompleteListener {
                            callback.onComplete()
                          }
                      }
                  }
                  .addOnFailureListener { excPost->
                    callback.onFailure(excPost.message ?: "Falha ao inserir um post")
                  }

              }
              .addOnFailureListener { excMe->
                callback.onFailure(excMe.message ?: "Falha ao buscar user logado")
              }
          }
          .addOnFailureListener { excDownload ->
            callback.onFailure(excDownload.message ?: "Falha ao baixar a foto")
          }
      }
      .addOnFailureListener{ exc ->
        callback.onFailure(exc.message ?: "Falha ao subir a foto")
      }
      .addOnCompleteListener {
        callback.onComplete()
      }
  }
}