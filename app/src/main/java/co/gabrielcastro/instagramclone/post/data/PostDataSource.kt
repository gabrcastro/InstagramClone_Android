package co.gabrielcastro.instagramclone.post.data

import android.net.Uri

interface PostDataSource {
  suspend fun fetchPicutres(): List<Uri>
}