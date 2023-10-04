package co.gabrielcastro.instagramclone.home

import co.gabrielcastro.instagramclone.common.base.BasePresenter
import co.gabrielcastro.instagramclone.common.base.BaseView
import co.gabrielcastro.instagramclone.common.model.Post

interface Home {

	interface Presenter: BasePresenter {
		fun fetchFeed()
		fun clear()
		fun logout()
	}
	interface View: BaseView<Presenter> {
		fun showProgress(enabled: Boolean)
		fun displayRequestFailure(message: String)
		fun displayEmptyPosts()
		fun displayFullPosts(posts: List<Post>)
	}

}