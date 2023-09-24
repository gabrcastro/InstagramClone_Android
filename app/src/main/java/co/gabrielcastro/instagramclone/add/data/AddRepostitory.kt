package co.gabrielcastro.instagramclone.add.data

import android.net.Uri
import co.gabrielcastro.instagramclone.common.base.RequestCallback

class AddRepostitory(
  private val remoteDataSource: AddFakeRemoveDataSource,
  private val localDataSource: AddLocalDataSource
) {

  fun createPost(uri: Uri, caption: String, callback: RequestCallback<Boolean>) {
    val userAuth = localDataSource.fetchSession()

    remoteDataSource.createPost(userAuth.uuid, uri, caption, object : RequestCallback<Boolean> {
      override fun onSuccess(data: Boolean) {
        callback.onSuccess(data)
      }

      override fun onFailure(message: String) {
        callback.onFailure(message)
      }

      override fun onComplete() {
        callback.onComplete()
      }
    })
  }

}