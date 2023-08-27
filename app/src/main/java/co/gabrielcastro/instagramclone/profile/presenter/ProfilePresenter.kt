package co.gabrielcastro.instagramclone.profile.presenter

import android.util.Patterns
import co.gabrielcastro.instagramclone.R
import co.gabrielcastro.instagramclone.common.base.RequestCallback
import co.gabrielcastro.instagramclone.common.model.Database
import co.gabrielcastro.instagramclone.common.model.Post
import co.gabrielcastro.instagramclone.common.model.UserAuth
import co.gabrielcastro.instagramclone.profile.Profile
import co.gabrielcastro.instagramclone.profile.data.ProfileRepository
import co.gabrielcastro.instagramclone.register.RegisterEmail
import co.gabrielcastro.instagramclone.register.data.RegisterCallback
import co.gabrielcastro.instagramclone.register.data.RegisterRepository
import java.lang.RuntimeException

class ProfilePresenter(
	private var view: Profile.View?,
	private val repository: ProfileRepository
) : Profile.Presenter {

	override var state: UserAuth? = null

	override fun fetchUserProfile() {
		view?.showProgress(true)
		val userUUID = Database.sessionAuth?.uuid ?: throw RuntimeException("User not found")
		repository.fetchUserProfile(userUUID, object : RequestCallback<UserAuth> {
			override fun onSuccess(data: UserAuth) {
				state = data
				view?.displayUserProfile(data)
			}

			override fun onFailure(message: String) {
				view?.displayRequestFailure(message)
			}

			override fun onComplete() {}
		})
	}

	override fun fetchUserPosts() {
		val userUUID = Database.sessionAuth?.uuid ?: throw RuntimeException("User not found")
		repository.fetchUserPosts(userUUID, object : RequestCallback<List<Post>> {
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