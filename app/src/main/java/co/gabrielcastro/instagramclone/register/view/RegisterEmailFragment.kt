package co.gabrielcastro.instagramclone.register.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import co.gabrielcastro.instagramclone.R
import co.gabrielcastro.instagramclone.common.base.DependencyInjector
import co.gabrielcastro.instagramclone.common.util.TxtWatcher
import co.gabrielcastro.instagramclone.databinding.FragmentRegisterEmailBinding
import co.gabrielcastro.instagramclone.register.RegisterEmail
import co.gabrielcastro.instagramclone.register.presenter.RegisterEmailPresenter

class RegisterEmailFragment : Fragment(R.layout.fragment_register_email), RegisterEmail.View {

    private var binding: FragmentRegisterEmailBinding? = null
    private var fragmentAttachListener: FragmentAttachListener? = null

    override lateinit var presenter: RegisterEmail.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterEmailBinding.bind(view)

        val repository = DependencyInjector.registerEmailRepository()
        presenter = RegisterEmailPresenter(this, repository)

        binding?.let {
            with(it) {
                registerTextviewLogin.setOnClickListener {
                    activity?.finish()
                }

                registerButtonNext.setOnClickListener {
                    presenter.create(
                        registerEditEmail.text.toString()
                    )
                }

                registerEditEmail.addTextChangedListener(watcher)
                registerEditEmail.addTextChangedListener(TxtWatcher {
                    displayEmailFailure(null)
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
        fragmentAttachListener = null
        presenter.onDestroy()
        super.onDestroy()
    }

    private val watcher = TxtWatcher {
        binding?.registerButtonNext?.isEnabled = binding?.registerEditEmail?.text.toString().isNotEmpty()
    }

    override fun displayEmailFailure(emailError: Int?) {
        binding?.registerEditEmail?.error = emailError?.let { getString(it) }
    }

    override fun showProgress(enabled: Boolean) {
        binding?.registerButtonNext?.showProgress(enabled)
    }
    override fun onEmailFailure(message: String) {
        binding?.registerEditEmail?.error = message
    }

    override fun goToNameAndPasswordScreen(email: String) {
        fragmentAttachListener?.goToNameAndPasswordScreen(email)
    }


}