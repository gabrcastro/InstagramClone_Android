package co.gabrielcastro.instagramclone.profile.data

import co.gabrielcastro.instagramclone.common.base.Cache
import co.gabrielcastro.instagramclone.common.base.RequestCallback
import co.gabrielcastro.instagramclone.common.model.Database
import co.gabrielcastro.instagramclone.common.model.Post
import co.gabrielcastro.instagramclone.common.model.User
import co.gabrielcastro.instagramclone.common.model.UserAuth
import com.google.firebase.auth.FirebaseAuth
import java.lang.RuntimeException

class ProfileLocalDataSource(
  private val profileCache: Cache<Pair<User, Boolean?>>,
  private val postsCache: Cache<List<Post>>
) : ProfileDataSource {

  override fun fetchUserProfile(userUUID: String, callback: RequestCallback<Pair<User, Boolean?>>) {
    val userAuth = profileCache.get(userUUID)
    if (userAuth != null) {
      callback.onSuccess(userAuth)
    } else {
      callback.onFailure("usuario nao encontrado")
    }

    callback.onComplete()
  }

  override fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>) {
    val posts = postsCache.get(userUUID)
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

  override fun putUser(response: Pair<User, Boolean?>?) {
    profileCache.put(response)
  }

  override fun putPosts(response: List<Post>?) {
    postsCache.put(response)
  }
}