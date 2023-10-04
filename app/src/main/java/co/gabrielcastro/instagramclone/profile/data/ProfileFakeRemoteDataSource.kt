package co.gabrielcastro.instagramclone.profile.data

import android.os.Handler
import android.os.Looper
import co.gabrielcastro.instagramclone.common.base.RequestCallback
import co.gabrielcastro.instagramclone.common.model.Database
import co.gabrielcastro.instagramclone.common.model.Post
import co.gabrielcastro.instagramclone.common.model.User

class ProfileFakeRemoteDataSource : ProfileDataSource {

	override fun fetchUserProfile(userUUID: String, callback: RequestCallback<Pair<User, Boolean?>>) {
		Handler(Looper.getMainLooper()).postDelayed({
			val userAuth = Database.usersAuth.firstOrNull { userUUID == it.uuid }

			if (userAuth != null) {
				if (userAuth == Database.sessionAuth) {
					// TODO: remove later - callback.onSuccess(Pair(userAuth, null))
				} else {
					val followeing = Database.followers[Database.sessionAuth!!.uuid]

					val destUser = followeing?.firstOrNull() { it == userUUID }

					// TODO: remove later - callback.onSuccess(Pair(userAuth, destUser != null))
				}
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

	override fun followUser(uuid: String, follow: Boolean, callback: RequestCallback<Boolean>) {
		Handler(Looper.getMainLooper()).postDelayed({
			var followers = Database.followers[Database.sessionAuth!!.uuid]

			if (followers == null) {
				followers = mutableSetOf()
				Database.followers[Database.sessionAuth!!.uuid] = followers
			}

			if (follow) {
				Database.followers[Database.sessionAuth!!.uuid]!!.add(uuid)
			} else {
				Database.followers[Database.sessionAuth!!.uuid]!!.remove(uuid)
			}
 		}, 500)
	}
}