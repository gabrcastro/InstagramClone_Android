package co.gabrielcastro.instagramclone.splash.data

import co.gabrielcastro.instagramclone.common.model.Database

class FakeLocalDataSource : SplashDataSource {
	override fun session(callback: SplashCallback) {
		if (Database.sessionAuth != null) {
			callback.onSuccess()
		} else {
			callback.onFailure()
		}
	}
}