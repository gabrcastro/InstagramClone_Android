package co.gabrielcastro.instagramclone.register.data

import android.net.Uri
import android.os.Handler
import android.os.Looper
import co.gabrielcastro.instagramclone.common.model.Database
import co.gabrielcastro.instagramclone.common.model.Photo
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
				val newUser =  UserAuth(UUID.randomUUID().toString(), name, email, password)
				val created = Database.usersAuth.add(newUser)
				if (created) {
					Database.sessionAuth = newUser
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
				val newPhoto = Photo(userAuth.uuid, photoUri)

				val created = Database.photos.add(newPhoto)

				if (created) {
					callback.onSuccess()
				} else {
					callback.onFailure(" Erro no servidor ")
				}
			}

			callback.onComplete()
		}, 2000)
	}


}
