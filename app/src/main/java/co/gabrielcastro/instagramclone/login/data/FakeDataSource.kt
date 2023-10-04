package co.gabrielcastro.instagramclone.login.data

import android.os.Handler
import android.os.Looper
import co.gabrielcastro.instagramclone.common.model.Database

class FakeDataSource : LoginDataSource {
    override fun login(email: String, password: String, callback: LoginCallback) {

        val userAuth = Database.usersAuth.firstOrNull { email == it.email }

        when {
            userAuth == null -> {
                callback.onFailure("Usuario nao encontrado")
            }

            userAuth.password != password -> {
                callback.onFailure("Senha incorreta")
            }

            else -> {
                Database.sessionAuth = userAuth
                callback.onSuccess()
            }
        }

        callback.onComplete()
    }

}
