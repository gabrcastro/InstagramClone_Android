package co.gabrielcastro.instagramclone.add.data

import android.net.Uri
import co.gabrielcastro.instagramclone.common.base.RequestCallback
import co.gabrielcastro.instagramclone.common.model.UserAuth

interface AddDataSource {

  fun createPost(userUUID: String, uri: Uri, caption: String, callback: RequestCallback<Boolean>) {
    throw UnsupportedOperationException()
  }

  fun fetchSession() : String { throw UnsupportedOperationException() }

}