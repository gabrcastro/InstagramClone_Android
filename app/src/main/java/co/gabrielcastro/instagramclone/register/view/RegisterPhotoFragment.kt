package co.gabrielcastro.instagramclone.register.view

import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import co.gabrielcastro.instagramclone.R
import co.gabrielcastro.instagramclone.common.base.DependencyInjector
import co.gabrielcastro.instagramclone.common.view.CropperImageFragment
import co.gabrielcastro.instagramclone.common.view.CustomDialog
import co.gabrielcastro.instagramclone.databinding.FragmentRegisterPhotoBinding
import co.gabrielcastro.instagramclone.register.RegisterPhoto
import co.gabrielcastro.instagramclone.register.presenter.RegisterPhotoPresenter

class RegisterPhotoFragment : Fragment(R.layout.fragment_register_photo), RegisterPhoto.View {

	private var binding: FragmentRegisterPhotoBinding? = null
	private var fragmentAttachListener: FragmentAttachListener? = null
	override lateinit var presenter: RegisterPhoto.Presenter
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setFragmentResultListener("cropKey") { requestKey, bundle ->
			val uri = bundle.getParcelable<Uri>(CropperImageFragment.KEY_URI)

			onCropImageResult(uri)
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding = FragmentRegisterPhotoBinding.bind(view)

		val repository = DependencyInjector.registerEmailRepository()
		presenter = RegisterPhotoPresenter(this, repository)

		binding?.let {
			with(it) {
				registerButtonJump.setOnClickListener {
					fragmentAttachListener?.goToMainScreen()
				}

				registerButtonNext.isEnabled = true
				registerButtonNext.setOnClickListener {
					openDialog()
				}
			}
		}


	}

	private fun openDialog() {
		val customDialog = CustomDialog(requireContext())
		customDialog.addButton(R.string.photo, R.string.gallery) {
			when (it.id) {
				R.string.photo -> {
					// open camera
					fragmentAttachListener?.goToCameraScreen()
				}

				R.string.gallery -> {
					// open gallery
					fragmentAttachListener?.goToGalleryScreen()
				}
			}
		}
		customDialog.show()
	}

	private fun onCropImageResult(uri: Uri?) {
		if (uri != null) {

			val bitmap = if (Build.VERSION.SDK_INT >= 28) {
				val source = ImageDecoder.createSource(requireContext().contentResolver, uri)
				ImageDecoder.decodeBitmap(source)
			} else {
				MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
			}

			binding?.registerImgProfile?.setImageBitmap(bitmap)
			presenter.updateUser(uri)
		}
	}

	override fun showProgress(enabled: Boolean) {
		binding?.registerButtonNext?.showProgress(enabled)
	}

	override fun onUpdateFailure(message: String) {
		Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
	}

	override fun onUpdateSuccess() {
		fragmentAttachListener?.goToMainScreen()
	}

	override fun onDestroy() {
		binding = null
		presenter.onDestroy()
		super.onDestroy()
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		if (context is FragmentAttachListener) {
			fragmentAttachListener = context
		}
	}
}