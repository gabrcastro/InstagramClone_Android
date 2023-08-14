package co.gabrielcastro.instagramclone.login.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import co.gabrielcastro.instagramclone.R
import co.gabrielcastro.instagramclone.common.util.TxtWatcher
import co.gabrielcastro.instagramclone.databinding.ActivityLoginBinding
import co.gabrielcastro.instagramclone.login.Login
import co.gabrielcastro.instagramclone.login.presentation.LoginPresenter

class LoginActivity : AppCompatActivity(), Login.View {

    private lateinit var binding: ActivityLoginBinding
    override lateinit var presenter: Login.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = LoginPresenter(this)

        with(binding) {
            loginEditEmail.addTextChangedListener(watcher)
            loginEditPassword.addTextChangedListener(watcher)
            loginButtonEnter.setOnClickListener {
                presenter.login(loginEditEmail.text.toString(), loginEditPassword.text.toString())
//                Handler(android.os.Looper.getMainLooper()).postDelayed({
//                    loginButtonEnter.showProgress(false)
//                }, 2000)
            }
        }
    }

    private val watcher = TxtWatcher {
        binding.loginButtonEnter.isEnabled =
            it.isNotEmpty()
    }

    override fun showProgress(enabled: Boolean) {
        binding.loginButtonEnter.showProgress(true)
    }

    override fun displayEmailFailure(emailError: Int?) {
        binding.loginEditEmail.error = emailError?.let { getString(emailError) }
    }

    override fun displayPasswordFailure(passwordError: Int?) {
        binding.loginEditPassword.error = passwordError?.let { getString(passwordError) }
    }

    override fun onUserAuthenticated() {
        // TODO: go to main screen
    }

    override fun onUserUnauthorized() {
        // TODO: show alert
    }
}