package co.gabrielcastro.instagramclone.register

import android.net.Uri
import co.gabrielcastro.instagramclone.common.base.BasePresenter
import co.gabrielcastro.instagramclone.common.base.BaseView

interface RegisterPhoto {
	interface Presenter : BasePresenter {
		fun updateUser(photoUri: Uri)
	}

	interface View : BaseView<Presenter> {
		fun showProgress(enabled: Boolean)
		fun onUpdateFailure(message: String)
		fun onUpdateSuccess()
	}
}
