package co.gabrielcastro.instagramclone.search.data

import co.gabrielcastro.instagramclone.common.base.RequestCallback
import co.gabrielcastro.instagramclone.common.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseSearchDataSource : SearchDataSource {
  override fun fetchUsers(name: String, callback: RequestCallback<List<User>>) {
    FirebaseFirestore.getInstance()
      .collection("/users")
      .whereGreaterThanOrEqualTo("name", name)
      .whereLessThanOrEqualTo("name", name + "\uf9ff")
      .get()
      .addOnSuccessListener { res ->
        val documents = res.documents
        val users =  mutableListOf<User>()
        for (document in documents) {
          val user = document.toObject(User::class.java)

          if (user != null && user.uuid != FirebaseAuth.getInstance().uid)
            users.add(user)

        }
        callback.onSuccess(users)
      }
      .addOnFailureListener { exc ->
        callback.onFailure(exc.message ?: "Falha ao buscar usuario")
      }
      .addOnCompleteListener {
        callback.onComplete()
      }

  }
}