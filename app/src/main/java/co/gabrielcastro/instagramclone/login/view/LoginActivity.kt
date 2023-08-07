package co.gabrielcastro.instagramclone.login.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import co.gabrielcastro.instagramclone.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val editTextEmail = findViewById<TextInputEditText>(R.id.login_edit_email)
        val editTextPassword = findViewById<TextInputEditText>(R.id.login_edit_password)
        val buttonEnter = findViewById<LoadingButton>(R.id.login_button_enter)

        editTextEmail.addTextChangedListener(watcher)
        editTextPassword.addTextChangedListener(watcher)

        buttonEnter.setOnClickListener {
            buttonEnter.showProgress(true)

            findViewById<TextInputLayout>(R.id.login_edit_email_input)
                .error = "Invalid e-mail"

            findViewById<TextInputLayout>(R.id.login_edit_password_input)
                .error = "Invalid password"

            Handler(Looper.getMainLooper()).postDelayed({
                buttonEnter.showProgress(false)

            }, 2000)
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