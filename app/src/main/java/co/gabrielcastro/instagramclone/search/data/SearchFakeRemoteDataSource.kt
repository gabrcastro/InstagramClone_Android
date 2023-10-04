package co.gabrielcastro.instagramclone.search.data

import android.os.Handler
import android.os.Looper
import co.gabrielcastro.instagramclone.common.base.RequestCallback
import co.gabrielcastro.instagramclone.common.model.Database
import co.gabrielcastro.instagramclone.common.model.Post
import co.gabrielcastro.instagramclone.common.model.User
import co.gabrielcastro.instagramclone.common.model.UserAuth
import kotlinx.coroutines.flow.callbackFlow

class SearchFakeRemoteDataSource : SearchDataSource {
	override fun fetchUsers(name: String, callback: RequestCallback<List<User>>) {
		Handler(Looper.getMainLooper()).postDelayed({
			val users = Database.usersAuth.filter {
				it.name.toLowerCase().startsWith(name.toLowerCase()) && it.uuid != Database.sessionAuth!!.uuid
			}

//			callback.onSuccess(users.toList())
			callback.onComplete()
		}, 2000)
	}

}