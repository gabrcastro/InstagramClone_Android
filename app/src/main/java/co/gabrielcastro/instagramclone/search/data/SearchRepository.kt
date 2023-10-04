package co.gabrielcastro.instagramclone.search.data

import co.gabrielcastro.instagramclone.common.base.RequestCallback
import co.gabrielcastro.instagramclone.common.model.User
import co.gabrielcastro.instagramclone.common.model.UserAuth

class SearchRepository(
	private val dataSource: SearchDataSource
) {
	fun fetchUsers(name: String, callback: RequestCallback<List<User>>) {
		dataSource.fetchUsers(name, object : RequestCallback<List<User>> {
			override fun onSuccess(data: List<User>) {
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