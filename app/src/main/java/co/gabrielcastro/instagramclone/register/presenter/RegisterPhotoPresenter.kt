package co.gabrielcastro.instagramclone.register.presenter

import android.net.Uri
import co.gabrielcastro.instagramclone.register.RegisterPhoto
import co.gabrielcastro.instagramclone.register.data.RegisterCallback
import co.gabrielcastro.instagramclone.register.data.RegisterRepository

class RegisterPhotoPresenter(
	private var view: RegisterPhoto.View?,
	private var repository: RegisterRepository,
) : RegisterPhoto.Presenter {

	override fun updateUser(photoUri: Uri) {
		view?.showProgress(true)

		repository.updateUser(photoUri, object : RegisterCallback {
			override fun onSuccess() {
				view?.onUpdateSuccess()
			}

			override fun onFailure(message: String) {
				view?.onUpdateFailure(message)
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