package co.gabrielcastro.instagramclone.home.data

import android.os.Handler
import android.os.Looper
import co.gabrielcastro.instagramclone.common.base.RequestCallback
import co.gabrielcastro.instagramclone.common.model.Database
import co.gabrielcastro.instagramclone.common.model.Post

class HomeFakeRemoteDataSource : HomeDataSource {

	override fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>) {
		Handler(Looper.getMainLooper()).postDelayed({
			val feed = Database.feeds[userUUID]

			callback.onSuccess(feed?.toList() ?: emptyList())

			callback.onComplete()
		}, 2000)
	}
}