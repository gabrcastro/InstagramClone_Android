package co.gabrielcastro.instagramclone.splash.presentation

import co.gabrielcastro.instagramclone.splash.Splash
import co.gabrielcastro.instagramclone.splash.data.SplashCallback
import co.gabrielcastro.instagramclone.splash.data.SplashRepository

class SplashPresenter(

	private var view: Splash.View?,
	private val repository: SplashRepository

) : Splash.Presenter {

	override fun onDestroy() {
		view = null
	}

	override fun authenticated() {
		repository.session(object : SplashCallback {
			override fun onSuccess() {
				view?.goToMainScreen()
			}

			override fun onFailure() {
				view?.goToLoginScreen()
			}
		})
	}
}