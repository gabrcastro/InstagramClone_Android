package co.gabrielcastro.instagramclone.home.data

import co.gabrielcastro.instagramclone.common.base.RequestCallback
import co.gabrielcastro.instagramclone.common.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import java.lang.RuntimeException

class FirebaseHomeDataSource : HomeDataSource {
  override fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>) {
    val uid = FirebaseAuth.getInstance().uid ?: throw RuntimeException("User not found")
    FirebaseFirestore.getInstance()
      .collection("/feeds")
      .document(uid)
      .collection("posts")
      .orderBy("timestamp", Query.Direction.DESCENDING)
      .get()
      .addOnSuccessListener { res ->
        val feed = mutableListOf<Post>()
        val documents = res.documents
        for (document in documents) {
          val post = document.toObject(Post::class.java)
          post?.let { feed.add(it) }
        }
        callback.onSuccess(feed)
      }
      .addOnFailureListener { exception ->
        callback.onFailure(exception.message ?: "Erro ao carregar feed")
      }
      .addOnCompleteListener {
        callback.onComplete()
      }
  }

  override fun logout() {
    FirebaseAuth.getInstance().signOut()
  }
}