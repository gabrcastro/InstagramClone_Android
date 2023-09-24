package co.gabrielcastro.instagramclone.profile.data

import co.gabrielcastro.instagramclone.common.base.RequestCallback
import co.gabrielcastro.instagramclone.common.model.Post
import co.gabrielcastro.instagramclone.common.model.UserAuth
import java.util.UUID

interface ProfileDataSource {
	fun fetchUserProfile(userUUID: String, callback: RequestCallback<UserAuth>)
	fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>)
	fun fetchSession() : UserAuth {
		throw UnsupportedOperationException()
	}
	fun putUser(response: UserAuth) {
		throw UnsupportedOperationException()
	}
	fun putPosts(response: List<Post>?) {
		throw UnsupportedOperationException()
	}
}