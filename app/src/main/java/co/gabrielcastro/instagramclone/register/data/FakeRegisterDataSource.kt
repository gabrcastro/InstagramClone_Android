package co.gabrielcastro.instagramclone.register.data

import android.net.Uri
import android.os.Handler
import android.os.Looper
import co.gabrielcastro.instagramclone.common.model.Database
import co.gabrielcastro.instagramclone.common.model.UserAuth
import java.util.UUID

class FakeRegisterDataSource : RegisterDataSource {
	override fun create(email: String, callback: RegisterCallback) {
		Handler(Looper.getMainLooper()).postDelayed({
			val userAuth = Database.usersAuth.firstOrNull { email == it.email }

			if (userAuth == null) {
				callback.onSuccess()
			} else {
				callback.onFailure("Usuario ja cadastrado")
			}

			callback.onComplete()
		}, 2000)
	}

	override fun create(
		email: String,
		name: String,
		password: String,
		callback: RegisterCallback
	) {
		Handler(Looper.getMainLooper()).postDelayed({
			val userAuth = Database.usersAuth.firstOrNull { email == it.email }

			if (userAuth != null) {
				callback.onFailure("Usuario ja cadastrado")

			} else {
				val newUser =  UserAuth(UUID.randomUUID().toString(), name, email, password, null)
				val created = Database.usersAuth.add(newUser)
				if (created) {
					Database.sessionAuth = newUser

					Database.followers[newUser.uuid] = hashSetOf()
					Database.posts[newUser.uuid] = hashSetOf()
					Database.feeds[newUser.uuid] = hashSetOf()

					callback.onSuccess()
				} else {
					callback.onFailure(" Erro no servidor ")
				}
			}

			callback.onComplete()
		}, 2000)
	}


	override fun updateUser(photoUri: Uri, callback: RegisterCallback) {
		Handler(Looper.getMainLooper()).postDelayed({
			val userAuth = Database.sessionAuth

			if (userAuth == null) {
				callback.onFailure("Usuario nao cadastrado")
			} else {
				val index = Database.usersAuth.indexOf(Database.sessionAuth)
				Database.usersAuth[index] = Database.sessionAuth!!.copy(photoUri = photoUri)
				Database.sessionAuth = Database.usersAuth[index]
					callback.onSuccess()
			}
			callback.onComplete()
		}, 2000)
	}


}
