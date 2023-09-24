package co.gabrielcastro.instagramclone.profile

import co.gabrielcastro.instagramclone.common.base.BasePresenter
import co.gabrielcastro.instagramclone.common.base.BaseView
import co.gabrielcastro.instagramclone.common.model.Post
import co.gabrielcastro.instagramclone.common.model.UserAuth

interface Profile {
	interface Presenter: BasePresenter {
		fun fetchUserProfile()
		fun fetchUserPosts()
		fun clear()
	}

	interface View: BaseView<Presenter> {
		fun showProgress(enabled: Boolean)
		fun displayUserProfile(userAuth: UserAuth)
		fun displayRequestFailure(message: String)
		fun displayEmptyPosts()
		fun displayFullPosts(posts: List<Post>)

	}
}