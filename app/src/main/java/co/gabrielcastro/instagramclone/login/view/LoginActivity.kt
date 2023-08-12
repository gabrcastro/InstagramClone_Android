package co.gabrielcastro.instagramclone.login.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import co.gabrielcastro.instagramclone.R
import co.gabrielcastro.instagramclone.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            loginEditEmail.addTextChangedListener(watcher)
            loginEditPassword.addTextChangedListener(watcher)
            loginButtonEnter.setOnClickListener {
                loginButtonEnter.showProgress(true)
                loginEditEmail.error = "Invalid e-mail"
                loginEditPassword.error = "Invalid password"

                Handler(android.os.Looper.getMainLooper()).postDelayed({
                    loginButtonEnter.showProgress(false)
                }, 2000)
            }
        }
    }

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            findViewById<LoadingButton>(R.id.login_button_enter).isEnabled =
                s.toString().isNotEmpty()
        }
        override fun afterTextChanged(s: Editable?) {
        }

    }
}