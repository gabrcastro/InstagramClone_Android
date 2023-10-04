package co.gabrielcastro.instagramclone.add.data

import co.gabrielcastro.instagramclone.common.model.Database
import co.gabrielcastro.instagramclone.common.model.UserAuth
import com.google.firebase.auth.FirebaseAuth
import java.lang.RuntimeException

class AddLocalDataSource : AddDataSource {
  override fun fetchSession(): String {
    //return Database.sessionAuth ?: throw RuntimeException("Usuario nao logado")
    return FirebaseAuth.getInstance().uid ?: throw RuntimeException("Usuario nao logado")
  }
}