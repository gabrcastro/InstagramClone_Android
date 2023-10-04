package co.gabrielcastro.instagramclone.search.presenter

import co.gabrielcastro.instagramclone.common.base.RequestCallback
import co.gabrielcastro.instagramclone.common.model.Database
import co.gabrielcastro.instagramclone.common.model.Post
import co.gabrielcastro.instagramclone.common.model.User
import co.gabrielcastro.instagramclone.common.model.UserAuth
import co.gabrielcastro.instagramclone.profile.Profile
import co.gabrielcastro.instagramclone.profile.data.ProfileRepository
import co.gabrielcastro.instagramclone.search.Search
import co.gabrielcastro.instagramclone.search.data.SearchRepository
import java.lang.RuntimeException

class SearchPresenter(
	private var view: Search.View?,
	private val repository: SearchRepository
) : Search.Presenter {

	override fun fetchUsers(name: String) {
		view?.showProgress(true)
		repository.fetchUsers(name, object : RequestCallback<List<User>> {
			override fun onSuccess(data: List<User>) {
				if (data.isEmpty()) {
					view?.displayEmptyUsers()
				} else {
					view?.displayFullUsers(data)
				}
			}

			override fun onFailure(message: String) {
				view?.displayEmptyUsers()
			}

			override fun onComplete() {}
		})
	}

	override fun onDestroy() {
		view = null
	}
}