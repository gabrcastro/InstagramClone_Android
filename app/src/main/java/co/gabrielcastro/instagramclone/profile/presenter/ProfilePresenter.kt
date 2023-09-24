package co.gabrielcastro.instagramclone.profile.presenter

import co.gabrielcastro.instagramclone.common.base.RequestCallback
import co.gabrielcastro.instagramclone.common.model.Database
import co.gabrielcastro.instagramclone.common.model.Post
import co.gabrielcastro.instagramclone.common.model.UserAuth
import co.gabrielcastro.instagramclone.profile.Profile
import co.gabrielcastro.instagramclone.profile.data.ProfileRepository
import java.lang.RuntimeException

class ProfilePresenter(
	private var view: Profile.View?,
	private val repository: ProfileRepository
) : Profile.Presenter {

	override fun clear() {
		repository.clearCache()
	}

	override fun fetchUserProfile() {
		view?.showProgress(true)
		repository.fetchUserProfile(object : RequestCallback<UserAuth> {
			override fun onSuccess(data: UserAuth) {
				view?.displayUserProfile(data)
			}

			override fun onFailure(message: String) {
				view?.displayRequestFailure(message)
			}

			override fun onComplete() {}
		})
	}

	override fun fetchUserPosts() {
//		val userUUID = Database.sessionAuth?.uuid ?: throw RuntimeException("User not found")
		repository.fetchUserPosts(object : RequestCallback<List<Post>> {
			override fun onSuccess(data: List<Post>) {
				if (data.isEmpty()) {
					view?.displayEmptyPosts()
				} else {
					view?.displayFullPosts(data)
				}
			}

			override fun onFailure(message: String) {
				view?.displayRequestFailure(message)
			}

			override fun onComplete() {
				view?.showProgress(false)
			}
		})
	}

	override fun onDestroy() {
		view = null
	}
}