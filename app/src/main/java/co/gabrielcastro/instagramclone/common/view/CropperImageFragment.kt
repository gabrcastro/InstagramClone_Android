package co.gabrielcastro.instagramclone.common.view

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import co.gabrielcastro.instagramclone.R
import co.gabrielcastro.instagramclone.databinding.FragmentImageCropBinding
import java.io.File

class CropperImageFragment : Fragment(R.layout.fragment_image_crop) {

	private var binding: FragmentImageCropBinding? = null

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding = FragmentImageCropBinding.bind(view)

		val uri = arguments?.getParcelable<Uri>(KEY_URI)

		binding?.let {
			with(it) {
				cropperContainer.setAspectRatio(1, 1)
				cropperContainer.setFixedAspectRatio(true)
				cropperContainer.setImageUriAsync(uri)

				cropperBtnCancel.setOnClickListener {
					parentFragmentManager.popBackStack() // forcar o botao de voltar
				}

				// toda vez que a imagem for salva
				cropperContainer.setOnCropImageCompleteListener { view, result ->
					// precisaremos enviar este resultado para a alguem que tiver declarado que quer ouvir
					setFragmentResult("cropKey", bundleOf(KEY_URI to result.uri)) // quem estiver escutando recebera a URI

					parentFragmentManager.popBackStack()
				}

				cropperBtnSave.setOnClickListener {
					val dir = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
					if (dir != null) {
						val uriToSaved = Uri.fromFile(File(dir.path, System.currentTimeMillis().toString() + ".jpeg"))
						cropperContainer.saveCroppedImageAsync(uriToSaved) // salvar imagem cortada nesse local


					}
				}
			}
		}
	}

	override fun onDestroy() {
		binding = null
		super.onDestroy()
	}

	companion object {
		const val KEY_URI = "key_uri"
	}

}