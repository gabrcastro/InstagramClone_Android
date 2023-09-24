package co.gabrielcastro.instagramclone.post

import android.net.Uri
import co.gabrielcastro.instagramclone.common.base.BasePresenter
import co.gabrielcastro.instagramclone.common.base.BaseView
import co.gabrielcastro.instagramclone.common.model.UserAuth

interface Post {

  interface Presenter: BasePresenter {
    fun fetchPictures()
    fun selectUri(uri: Uri)
    fun getSelectedUri(): Uri?
  }

  interface View: BaseView<Presenter> {
    fun showProgress(enabled: Boolean)
    fun displayFullPictures(posts: List<Uri>)
    fun displayEmptyPictures()
    fun displayRequestFailure(message: String)
  }
}