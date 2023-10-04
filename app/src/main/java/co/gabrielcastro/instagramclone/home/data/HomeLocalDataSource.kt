package co.gabrielcastro.instagramclone.home.data

import co.gabrielcastro.instagramclone.common.base.Cache
import co.gabrielcastro.instagramclone.common.base.RequestCallback
import co.gabrielcastro.instagramclone.common.model.Database
import co.gabrielcastro.instagramclone.common.model.Post
import co.gabrielcastro.instagramclone.common.model.UserAuth
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import java.lang.RuntimeException

class HomeLocalDataSource(
  private val feedCache: Cache<List<Post>>
) : HomeDataSource {

  override fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>) {
    val posts = feedCache.get(userUUID)
    if (posts != null) {
      callback.onSuccess(posts)
    } else {
      callback.onFailure("posts nao existem")
    }

    callback.onComplete()
  }

  override fun fetchSession(): String {
    return FirebaseAuth.getInstance().uid ?: throw RuntimeException("usuario nao logado")
  }

  override fun putFeed(response: List<Post>?) {
    feedCache.put(response)
  }
}