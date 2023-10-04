package co.gabrielcastro.instagramclone.search

import co.gabrielcastro.instagramclone.common.base.BasePresenter
import co.gabrielcastro.instagramclone.common.base.BaseView
import co.gabrielcastro.instagramclone.common.model.User
import co.gabrielcastro.instagramclone.common.model.UserAuth

interface Search {
  interface Presenter : BasePresenter {
    fun fetchUsers(name: String)

  }

  interface View: BaseView<Presenter> {
    fun showProgress(enabled: Boolean)
    fun displayFullUsers(users: List<User>)
    fun displayEmptyUsers()
  }
}