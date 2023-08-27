package co.gabrielcastro.instagramclone.splash.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.gabrielcastro.instagramclone.common.base.DependencyInjector
import co.gabrielcastro.instagramclone.common.extension.animationEnd
import co.gabrielcastro.instagramclone.databinding.ActivitySplashBinding
import co.gabrielcastro.instagramclone.login.view.LoginActivity
import co.gabrielcastro.instagramclone.main.view.MainActivity
import co.gabrielcastro.instagramclone.splash.Splash
import co.gabrielcastro.instagramclone.splash.presentation.SplashPresenter

class SplashActivity : AppCompatActivity(), Splash.View {

	private lateinit var binding: ActivitySplashBinding
	override lateinit var presenter: Splash.Presenter
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivitySplashBinding.inflate(layoutInflater)
		setContentView(binding.root)

		val repository = DependencyInjector.splashRepository()
		presenter = SplashPresenter(this, repository)

		// animacao
		binding.splashImg.animate().apply {
			setListener(animationEnd {
				presenter.authenticated()
			})
			duration = 2000
			alpha(1.0f)
			start()
		}

	}

	override fun onDestroy() {
		presenter.onDestroy()
		super.onDestroy()
	}

	override fun goToMainScreen() {
		binding.splashImg.animate().apply {
			setListener(animationEnd {
				val intent = Intent(baseContext, MainActivity::class.java)
				intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
				startActivity(intent)
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
			})
			duration = 1000
			startDelay = 1000
			alpha(0.0f)
			start()
		}

	}

	override fun goToLoginScreen() {
		binding.splashImg.animate().apply {
			setListener(animationEnd {
				val intent = Intent(baseContext, LoginActivity::class.java)
				intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
				startActivity(intent)
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
			})
			duration = 1000
			startDelay = 1000
			alpha(0.0f)
			start()
		}
	}
}