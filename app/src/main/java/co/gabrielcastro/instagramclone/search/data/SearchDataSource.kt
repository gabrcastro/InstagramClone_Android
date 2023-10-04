package co.gabrielcastro.instagramclone.search.data

import co.gabrielcastro.instagramclone.common.base.RequestCallback
import co.gabrielcastro.instagramclone.common.model.User

interface SearchDataSource {
  fun fetchUsers(name: String, callback: RequestCallback<List<User>>)
}