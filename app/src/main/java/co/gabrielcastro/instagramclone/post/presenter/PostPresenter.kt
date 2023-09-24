package co.gabrielcastro.instagramclone.post.presenter

import android.net.Uri
import co.gabrielcastro.instagramclone.common.base.RequestCallback
import co.gabrielcastro.instagramclone.common.model.UserAuth
import co.gabrielcastro.instagramclone.post.Post
import co.gabrielcastro.instagramclone.post.data.PostRepository
import co.gabrielcastro.instagramclone.profile.Profile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class PostPresenter(
	private var view: Post.View?,
	private val repository: PostRepository
) : Post.Presenter, CoroutineScope {
	private var uri: Uri? = null
	private val job = Job()
	override val coroutineContext: CoroutineContext = job + Dispatchers.IO

	override fun fetchPictures() {
		// aqui acontece a chamada na thread MAIN (UI)
		launch {
			// aqui acontece a chamada paralela (coroutine IO)
			val pictures = repository.fetchPicures()

			withContext(Dispatchers.Main) {
				view?.showProgress(true)

				// aqui executa de volta na MAIN Thread
				if (pictures.isEmpty()) {
					view?.displayEmptyPictures()
				} else {
					view?.displayFullPictures(pictures)
				}

				view?.showProgress(false)
			}

		}
	}

	override fun selectUri(uri: Uri) {
		this.uri = uri
	}

	override fun getSelectedUri(): Uri? {
		return uri
	}

	override fun onDestroy() {
		job.cancel()
		view = null
	}
}