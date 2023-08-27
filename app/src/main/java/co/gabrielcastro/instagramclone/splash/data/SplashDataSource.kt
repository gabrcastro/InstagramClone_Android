package co.gabrielcastro.instagramclone.splash.data

interface SplashDataSource {
	fun session(callback: SplashCallback)
}