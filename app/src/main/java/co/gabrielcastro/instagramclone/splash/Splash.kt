package co.gabrielcastro.instagramclone.splash

import co.gabrielcastro.instagramclone.common.base.BasePresenter
import co.gabrielcastro.instagramclone.common.base.BaseView

interface Splash {

	interface Presenter : BasePresenter {
		fun authenticated()
	}

	interface View : BaseView<Presenter> {
		fun goToMainScreen()
		fun goToLoginScreen()
	}

}