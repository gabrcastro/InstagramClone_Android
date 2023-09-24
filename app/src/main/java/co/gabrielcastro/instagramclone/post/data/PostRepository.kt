package co.gabrielcastro.instagramclone.post.data

import android.net.Uri

class PostRepository(private val dataSource: PostDataSource) {
  suspend fun fetchPicures(): List<Uri> = dataSource.fetchPicutres()
}