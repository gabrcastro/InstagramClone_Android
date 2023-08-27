package co.gabrielcastro.instagramclone.register

import androidx.annotation.StringRes
import co.gabrielcastro.instagramclone.common.base.BasePresenter
import co.gabrielcastro.instagramclone.common.base.BaseView

interface RegisterNameAndPassword {
    interface Presenter : BasePresenter {
        fun create(email: String, name: String, password: String, confirmPassword: String)
    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayNameFailure(@StringRes nameError: Int?)
        fun displayPasswordFailure(@StringRes passwordError: Int?)
        fun displayConfirmPasswordFailure(@StringRes passwordError: Int?)
        fun onCreateSuccess(name: String)
        fun onCreateFailure(message: String)
    }
}