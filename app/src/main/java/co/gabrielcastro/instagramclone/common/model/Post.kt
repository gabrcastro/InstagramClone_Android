package co.gabrielcastro.instagramclone.common.model


data class Post(
  val uuid: String? = null,
  val url: String? = null,
  val caption: String? = null,
  val timestamp: Long = 0,
  val publisher: User? = null,
)
