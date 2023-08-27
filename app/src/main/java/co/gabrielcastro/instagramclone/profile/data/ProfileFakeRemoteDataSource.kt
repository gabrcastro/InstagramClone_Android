package co.gabrielcastro.instagramclone.profile.data

import android.os.Handler
import android.os.Looper
import co.gabrielcastro.instagramclone.common.base.RequestCallback
import co.gabrielcastro.instagramclone.common.model.Database
import co.gabrielcastro.instagramclone.common.model.Post
import co.gabrielcastro.instagramclone.common.model.UserAuth

class ProfileFakeRemoteDataSource : ProfileDataSource {

	override fun fetchUserProfile(userUUID: String, callback: RequestCallback<UserAuth>) {
		Handler(Looper.getMainLooper()).postDelayed({
			val userAuth = Database.usersAuth.firstOrNull { userUUID == it.uuid }

			if (userAuth != null) {
				callback.onSuccess(userAuth)
			} else {
				callback.onFailure("Usuario nao encontrado")
			}

			callback.onComplete()
		}, 2000)
	}

	override fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>) {
		Handler(Looper.getMainLooper()).postDelayed({
			val posts = Database.posts[userUUID]

			callback.onSuccess(posts?.toList() ?: emptyList())

			callback.onComplete()
		}, 2000)
	}
}