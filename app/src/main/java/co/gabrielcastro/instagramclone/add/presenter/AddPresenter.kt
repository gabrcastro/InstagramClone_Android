package co.gabrielcastro.instagramclone.add.presenter

import android.net.Uri
import co.gabrielcastro.instagramclone.add.Add
import co.gabrielcastro.instagramclone.add.data.AddRepostitory
import co.gabrielcastro.instagramclone.common.base.RequestCallback

class AddPresenter(
  private var view: Add.View? = null,
  private val repostitory: AddRepostitory
) : Add.Presenter {
  override fun createPost(uri: Uri, caption: String) {
    view?.showProgress(true)
    repostitory.createPost(uri, caption, object : RequestCallback<Boolean> {
      override fun onSuccess(data: Boolean) {
        if (data) {
          view?.displayRequestSuccess()
        } else {
          view?.displayRequestFailure("Internal error")
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