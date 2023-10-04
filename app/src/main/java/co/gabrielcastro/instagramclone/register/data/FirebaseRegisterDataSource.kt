package co.gabrielcastro.instagramclone.register.data

import android.net.Uri
import co.gabrielcastro.instagramclone.common.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class FirebaseRegisterDataSource : RegisterDataSource {

  override fun create(email: String, callback: RegisterCallback) {
    FirebaseFirestore.getInstance()
      .collection("/users")
      .whereEqualTo("email", email)
      .get()
      .addOnSuccessListener { documents ->
        if (documents.isEmpty) {
          callback.onSuccess()
        } else {
          callback.onFailure("User already exist")
        }
      }
      .addOnFailureListener { exception ->
        callback.onFailure(exception?.message ?: "Internal server error")
      }
      .addOnCompleteListener {
        callback.onComplete()
      }

  }

  override fun create(email: String, name: String, password: String, callback: RegisterCallback) {
    FirebaseAuth.getInstance()
      .createUserWithEmailAndPassword(email, password)
      .addOnSuccessListener {
        val uid = it.user?.uid
        if (uid == null) {
          callback.onFailure("Erro interno no servidor")
        } else {
          FirebaseFirestore.getInstance()
            .collection("/users")
            .document(uid)
            .set(
              hashMapOf(
                "name" to name,
                "email" to email,
                "followers" to 0,
                "following" to 0,
                "portCount" to 0,
                "uuid" to uid,
                "photoUrl" to null,
              )
            )
            .addOnSuccessListener {
              callback.onSuccess()
            }
            .addOnFailureListener {
              callback.onFailure(it.message ?: "Erro interno no servidor")
            }
            .addOnCompleteListener {
              callback.onComplete()
            }
        }
      }
      .addOnFailureListener {
        callback.onFailure(it.message ?: "Erro interno no servidor")
      }
  }

  override fun updateUser(photoUri: Uri, callback: RegisterCallback) {
    val uid = FirebaseAuth.getInstance().uid
    if (uid == null || photoUri.lastPathSegment == null) {
      callback.onFailure("User not found")
      return
    }

    val storageRef = FirebaseStorage.getInstance().reference
    val imgRef = storageRef.child("images/")
      .child(uid)
      .child(photoUri.lastPathSegment!!)

    imgRef.putFile(photoUri)
      .addOnSuccessListener { result ->
        imgRef.downloadUrl
          .addOnSuccessListener { res ->
            val userRef = FirebaseFirestore.getInstance().collection("/users")
              .document(uid)

            userRef.get()
              .addOnSuccessListener { document ->
                // alterar sua propriedade da foto
                val user = document.toObject(User::class.java)
                val newUser = user?.copy(photoUrl = res.toString())
                if (newUser != null) {
                  userRef.set(newUser)
                    .addOnSuccessListener {
                      callback.onSuccess()
                    }
                    .addOnFailureListener { exception ->
                      callback.onFailure(exception.message ?: "Falha ao atualizar a foto")
                    }
                    .addOnCompleteListener {
                      callback.onComplete()
                    }
                }
              }
          }
          .addOnFailureListener {
            callback.onFailure(it.message ?: "Falha ao subir a foto")
          }
      }
  }
}