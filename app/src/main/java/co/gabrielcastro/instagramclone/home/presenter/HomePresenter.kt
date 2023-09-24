package co.gabrielcastro.instagramclone.home.presenter

import co.gabrielcastro.instagramclone.common.base.RequestCallback
import co.gabrielcastro.instagramclone.common.model.Post
import co.gabrielcastro.instagramclone.home.Home
import co.gabrielcastro.instagramclone.home.data.HomeRepository

class HomePresenter(
  private var view: Home.View?,
  private val repository: HomeRepository
) : Home.Presenter {

  override fun clear() {
    repository.clearCache()
  }

  override fun fetchFeed() {
    view?.showProgress(true)

    repository.fetchFeed(object : RequestCallback<List<Post>> {
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