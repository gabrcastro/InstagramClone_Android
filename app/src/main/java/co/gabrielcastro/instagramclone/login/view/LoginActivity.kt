package co.gabrielcastro.instagramclone.login.view

import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.core.content.ContextCompat
import co.gabrielcastro.instagramclone.R
import co.gabrielcastro.instagramclone.common.base.DependencyInjector
import co.gabrielcastro.instagramclone.common.util.TxtWatcher
import co.gabrielcastro.instagramclone.databinding.ActivityLoginBinding
import co.gabrielcastro.instagramclone.login.Login
import co.gabrielcastro.instagramclone.login.data.FakeDataSource
import co.gabrielcastro.instagramclone.login.data.LoginRepository
import co.gabrielcastro.instagramclone.login.presentation.LoginPresenter
import co.gabrielcastro.instagramclone.main.view.MainActivity
import co.gabrielcastro.instagramclone.register.view.RegisterActivity

class LoginActivity : AppCompatActivity(), Login.View {

    private lateinit var binding: ActivityLoginBinding
    override lateinit var presenter: Login.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = LoginPresenter(this, DependencyInjector.loginRepository())

        with(binding) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                when(resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                    Configuration.UI_MODE_NIGHT_YES -> {
                        loginImageLogo.imageTintList = ColorStateList.valueOf(Color.WHITE)
                    }
                    Configuration.UI_MODE_NIGHT_NO -> {
                        loginImageLogo.imageTintList = ColorStateList.valueOf(Color.BLACK)
                    }
                }
            }

            loginEditEmail.addTextChangedListener(watcher)
            loginEditEmail.addTextChangedListener( TxtWatcher {
                displayEmailFailure(null)
            } )
            loginEditPassword.addTextChangedListener(watcher)
            loginEditPassword.addTextChangedListener(TxtWatcher {
                displayPasswordFailure(null)
            })
            loginButtonEnter.setOnClickListener {
                presenter.login(loginEditEmail.text.toString(), loginEditPassword.text.toString())
            }
            loginTextviewRegister.setOnClickListener {
                goToRegisterScreen()
            }
        }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    private val watcher = TxtWatcher {
        binding.loginButtonEnter.isEnabled = binding.loginEditEmail.text.toString().isNotEmpty()
                && binding.loginEditPassword.text.toString().isNotEmpty()
    }

    private fun goToRegisterScreen() {
        startActivity(Intent(this, RegisterActivity::class.java))
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
        // go to main screen
        val intent =  Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onUserUnauthorized(message: String) {
        // show alert
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}