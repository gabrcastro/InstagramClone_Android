package co.gabrielcastro.instagramclone.common.model

import android.net.Uri
import java.util.UUID

data class Post(
	val uuid: UUID,
	val uri: Uri,
	val caption: String,
	val timestamp: Long,
)
