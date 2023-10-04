package co.gabrielcastro.instagramclone.splash.data

import com.google.firebase.auth.FirebaseAuth

class FirebaseSplashDataSource : SplashDataSource {
	override fun session(callback: SplashCallback) {
		if ( FirebaseAuth.getInstance().uid != null ) {
			callback.onSuccess()
		} else {
			callback.onFailure()
		}
	}
}