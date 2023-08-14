package co.gabrielcastro.instagramclone.login

import co.gabrielcastro.instagramclone.common.base.BasePresenter
import co.gabrielcastro.instagramclone.common.base.BaseView

interface Login {

    // presenter camada
    interface Presenter: BasePresenter {
        fun login(email: String, password: String)
    }

    // view camada
    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayEmailFailure(emailError: Int?)
        fun displayPasswordFailure(passwordError: Int?)
        fun onUserAuthenticated()
        fun onUserUnauthorized()
    }
}