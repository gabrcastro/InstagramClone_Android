package co.gabrielcastro.instagramclone.profile.data

import co.gabrielcastro.instagramclone.common.base.RequestCallback
import co.gabrielcastro.instagramclone.common.model.Post
import co.gabrielcastro.instagramclone.common.model.UserAuth

class ProfileRepository(
	private val dataSourceFactory: ProfileDataSourceFactory
) {

	fun clearCache() {
		val localDataSource = dataSourceFactory.createLocalDataSource()
		localDataSource.putPosts(null)
	}

	fun fetchUserProfile(callback: RequestCallback<UserAuth>) {
		val localDataSource = dataSourceFactory.createLocalDataSource()
		val userAuth = localDataSource.fetchSession()
		val dataSource = dataSourceFactory.createFromUser()
		dataSource.fetchUserProfile(userAuth.uuid, object : RequestCallback<UserAuth> {
			override fun onSuccess(data: UserAuth) {
				localDataSource.putUser(data)
				callback.onSuccess(data)
			}

			override fun onFailure(message: String) {
				callback.onFailure(message)
			}

			override fun onComplete() {
				callback.onComplete()
			}
		})
	}
	fun fetchUserPosts(callback: RequestCallback<List<Post>>) {
		val localDataSource = dataSourceFactory.createLocalDataSource()
		val userAuth = localDataSource.fetchSession()
		val dataSource = dataSourceFactory.createFromPosts()

		dataSource.fetchUserPosts(userAuth.uuid, object : RequestCallback<List<Post>> {
			override fun onSuccess(data: List<Post>) {
				localDataSource.putPosts(data)
				callback.onSuccess(data)
			}

			override fun onFailure(message: String) {
				callback.onFailure(message)
			}

			override fun onComplete() {
				callback.onComplete()
			}
		})
	}

}