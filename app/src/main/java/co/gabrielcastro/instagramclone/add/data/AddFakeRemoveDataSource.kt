package co.gabrielcastro.instagramclone.add.data

import android.net.Uri
import android.os.Handler
import android.os.Looper
import co.gabrielcastro.instagramclone.common.base.RequestCallback
import co.gabrielcastro.instagramclone.common.model.Database
import co.gabrielcastro.instagramclone.common.model.Post
import java.util.UUID

class AddFakeRemoveDataSource : AddDataSource {

  override fun createPost(
    userUUID: String,
    uri: Uri,
    caption: String,
    callback: RequestCallback<Boolean>
  ) {
    Handler(Looper.getMainLooper()).postDelayed({
      var posts = Database.posts[userUUID]
      if (posts == null) {
        posts = mutableSetOf()
        Database.posts[userUUID] = posts
      }

      val post = Post(UUID.randomUUID().toString(), null, caption, System.currentTimeMillis(), null)
      posts.add(post)

      var followers = Database.followers[userUUID]
      if (followers == null) {
        followers = mutableSetOf()
        Database.followers[userUUID] = followers
      } else {
        for (follower in followers) {
          Database.feeds[follower]?.add(post)
        }

        Database.feeds[userUUID]?.add(post)
      }

      callback.onSuccess(true)
    }, 1000)
  }

}