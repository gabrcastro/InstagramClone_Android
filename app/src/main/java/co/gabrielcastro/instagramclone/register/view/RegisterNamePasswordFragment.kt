package co.gabrielcastro.instagramclone.register.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import co.gabrielcastro.instagramclone.R
import co.gabrielcastro.instagramclone.common.base.DependencyInjector
import co.gabrielcastro.instagramclone.common.util.TxtWatcher
import co.gabrielcastro.instagramclone.databinding.FragmentRegisterNamePasswordBinding
import co.gabrielcastro.instagramclone.register.RegisterNameAndPassword
import co.gabrielcastro.instagramclone.register.presenter.RegisterNameAndPasswordlPresenter

class RegisterNamePasswordFragment : Fragment(R.layout.fragment_register_name_password),
    RegisterNameAndPassword.View {

    private var binding: FragmentRegisterNamePasswordBinding? = null
    private var fragmentAttachListener: FragmentAttachListener? = null

    override lateinit var presenter: RegisterNameAndPassword.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterNamePasswordBinding.bind(view)

        val repository = DependencyInjector.registerEmailRepository()
        presenter = RegisterNameAndPasswordlPresenter(this, repository)

        val email = arguments?.getString(KEY_EMAIL) ?: throw IllegalArgumentException("Email not found")

        binding?.let {
            with(it) {
                registerTextviewRegister.setOnClickListener {
                    activity?.finish()
                }

                registerNamePasswordButtonNext.setOnClickListener {
                    Log.i("test", "clicou no botao de ir para tela de welcome")
                    presenter.create(
                        email,
                        registerEditName.text.toString(),
                        registerEditPassword.text.toString(),
                        registerEditConfirmPassword.text.toString()
                    )
                }

                registerEditName.addTextChangedListener(watcher)
                registerEditPassword.addTextChangedListener(watcher)
                registerEditConfirmPassword.addTextChangedListener(watcher)

                registerEditName.addTextChangedListener( TxtWatcher {
                    displayNameFailure(null)
                })

                registerEditPassword.addTextChangedListener( TxtWatcher {
                    displayNameFailure(null)
                })

                registerEditConfirmPassword.addTextChangedListener( TxtWatcher {
                    displayNameFailure(null)
                })
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentAttachListener) {
            fragmentAttachListener = context
        }
    }

    override fun onDestroy() {
        binding = null
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun showProgress(enabled: Boolean) {
        binding?.registerNamePasswordButtonNext?.showProgress(enabled)
    }

    override fun displayNameFailure(nameError: Int?) {
        binding?.registerEditNameInput?.error = nameError?.let { getString(it) }
    }

    override fun displayPasswordFailure(passwordError: Int?) {
        binding?.registerEditPasswordInput?.error = passwordError?.let { getString(it) }
    }

    override fun displayConfirmPasswordFailure(passwordError: Int?) {
        binding?.registerEditConfirmPasswordInput?.error = passwordError?.let { getString(it) }
    }

    override fun onCreateSuccess(name: String) {
        fragmentAttachListener?.goToWelcomeScreen(name)
    }

    override fun onCreateFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private val watcher = TxtWatcher {
        binding?.registerNamePasswordButtonNext?.isEnabled = binding?.registerEditName?.text.toString().isNotEmpty()
                && binding?.registerEditPassword?.text.toString().isNotEmpty()
                && binding?.registerEditConfirmPassword?.text.toString().isNotEmpty()
    }

    companion object {
        const val KEY_EMAIL = "key_email"
    }
}