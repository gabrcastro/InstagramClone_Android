package co.gabrielcastro.instagramclone.register.presenter

import co.gabrielcastro.instagramclone.R
import co.gabrielcastro.instagramclone.register.RegisterNameAndPassword
import co.gabrielcastro.instagramclone.register.data.RegisterCallback
import co.gabrielcastro.instagramclone.register.data.RegisterRepository

class RegisterNameAndPasswordlPresenter(
    private var view: RegisterNameAndPassword.View?,
    private val repository: RegisterRepository
): RegisterNameAndPassword.Presenter {

    override fun create(email: String, name: String, password: String, confirm: String) {
        val isNameValid = name.length >= 4
        val isPasswValid = password.length >= 8
        val isConfirmValid = password == confirm

        if (!isNameValid) {
            view?.displayNameFailure(R.string.invalid_name)
        } else {
            view?.displayNameFailure(null)
        }

        if (!isPasswValid) {
            view?.displayPasswordFailure(R.string.invalid_password)
        } else {
            if (!isConfirmValid) {
                view?.displayPasswordFailure(R.string.password_not_equal)
            } else {
                view?.displayPasswordFailure(null)
            }
        }

        if (isNameValid && isPasswValid && isConfirmValid) {
            view?.showProgress(true)

            repository.create(email, name, password, object : RegisterCallback {
                override fun onSuccess() {
                    view?.onCreateSuccess(name)
                }

                override fun onFailure(message: String) {
                    view?.onCreateFailure(message)
                }

                override fun onComplete() {
                    view?.showProgress(false)
                }

            })
        }
    }

    override fun onDestroy() {
        view = null
    }
}