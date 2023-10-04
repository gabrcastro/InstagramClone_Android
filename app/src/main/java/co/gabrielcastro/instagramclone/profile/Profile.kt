package co.gabrielcastro.instagramclone.profile

import co.gabrielcastro.instagramclone.common.base.BasePresenter
import co.gabrielcastro.instagramclone.common.base.BaseView
import co.gabrielcastro.instagramclone.common.model.Post
import co.gabrielcastro.instagramclone.common.model.User
import co.gabrielcastro.instagramclone.common.model.UserAuth

interface Profile {
	interface Presenter: BasePresenter {
		fun fetchUserProfile(uuid: String?)
		fun fetchUserPosts(uuid: String?)
		fun followUser(uuid: String, follow: Boolean)
		fun clear()
	}

	interface View: BaseView<Presenter> {
		fun showProgress(enabled: Boolean)
		fun displayUserProfile(userAuth: Pair<User, Boolean?>)
		fun displayRequestFailure(message: String)
		fun displayEmptyPosts()
		fun displayFullPosts(posts: List<Post>)
		fun followUpdated()
	}
}