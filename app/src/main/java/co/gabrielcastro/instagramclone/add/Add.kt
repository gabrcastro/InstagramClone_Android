package co.gabrielcastro.instagramclone.add

import android.net.Uri
import co.gabrielcastro.instagramclone.common.base.BasePresenter
import co.gabrielcastro.instagramclone.common.base.BaseView

interface Add {

  interface Presenter : BasePresenter{
    fun createPost(uri: Uri, caption: String)
  }

  interface View : BaseView<Presenter> {
    fun showProgress(enabled: Boolean)
    fun displayRequestSuccess()
    fun displayRequestFailure(message: String)
  }
}