package co.gabrielcastro.instagramclone.add.data

import co.gabrielcastro.instagramclone.common.model.Database
import co.gabrielcastro.instagramclone.common.model.UserAuth
import java.lang.RuntimeException

class AddLocalDataSource : AddDataSource {
  override fun fetchSession(): UserAuth {
    return Database.sessionAuth ?: throw RuntimeException("Usuario nao logado")
  }
}