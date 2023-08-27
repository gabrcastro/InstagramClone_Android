package co.gabrielcastro.instagramclone.profile.data

import co.gabrielcastro.instagramclone.common.base.RequestCallback
import co.gabrielcastro.instagramclone.common.model.Post
import co.gabrielcastro.instagramclone.common.model.UserAuth
import java.util.UUID

class ProfileRepository(
	private val dataSource: ProfileDataSource
) {

	fun fetchUserProfile(userUUID: String, callback: RequestCallback<UserAuth>) {
		dataSource.fetchUserProfile(userUUID, callback)
	}
	fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>) {
		dataSource.fetchUserPosts(userUUID, callback)
	}

}