package co.gabrielcastro.instagramclone.home.data

import co.gabrielcastro.instagramclone.common.base.RequestCallback
import co.gabrielcastro.instagramclone.common.model.Post
import co.gabrielcastro.instagramclone.common.model.UserAuth

interface HomeDataSource {
	fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>)
	fun fetchSession() : UserAuth {
		throw UnsupportedOperationException()
	}
	fun putFeed(response: List<Post>?) {
		throw UnsupportedOperationException()
	}
}