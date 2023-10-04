package co.gabrielcastro.instagramclone.profile.data

import co.gabrielcastro.instagramclone.common.base.RequestCallback
import co.gabrielcastro.instagramclone.common.model.Post
import co.gabrielcastro.instagramclone.common.model.User
import co.gabrielcastro.instagramclone.common.model.UserAuth

interface ProfileDataSource {
	fun fetchUserProfile(userUUID: String, callback: RequestCallback<Pair<User, Boolean?>>)
	fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>)
	fun fetchSession() : String {
		throw UnsupportedOperationException()
	}
	fun followUser(uuid: String, follow: Boolean, callback: RequestCallback<Boolean>) {
		throw UnsupportedOperationException()
	}
	fun putUser(response: Pair<User, Boolean?>?) {
		throw UnsupportedOperationException()
	}
	fun putPosts(response: List<Post>?) {
		throw UnsupportedOperationException()
	}
}