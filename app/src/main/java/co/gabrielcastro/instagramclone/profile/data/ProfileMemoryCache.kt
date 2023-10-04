package co.gabrielcastro.instagramclone.profile.data

import co.gabrielcastro.instagramclone.common.base.Cache
import co.gabrielcastro.instagramclone.common.model.User
import co.gabrielcastro.instagramclone.common.model.UserAuth

object ProfileMemoryCache : Cache<Pair<User, Boolean?>> {
  private var userAuth : Pair<User, Boolean?>? = null

  override fun isCached(): Boolean {
    return userAuth != null
  }

  override fun get(key: String): Pair<User, Boolean?>? {
    if (userAuth?.first?.uuid === key) {
      return userAuth
    }

    return null
  }

  override fun put(data: Pair<User, Boolean?>?) {
    userAuth = data
  }
}