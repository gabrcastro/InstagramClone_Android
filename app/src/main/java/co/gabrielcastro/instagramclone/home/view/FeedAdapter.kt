package co.gabrielcastro.instagramclone.home.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.gabrielcastro.instagramclone.R
import co.gabrielcastro.instagramclone.common.model.Post

class FeedAdapter() : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

  var items: List<Post> = mutableListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
    // layout que vamos criar
    return FeedViewHolder(
      LayoutInflater.from(parent.context).inflate(R.layout.item_post_list, parent, false)
    )
  }

  override fun getItemCount(): Int {
    // quantidade de items da celula
    return items.size
  }

  override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
    // devolver posicao
    holder.bind(items[position])
  }

  class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(post: Post) {
      itemView.findViewById<ImageView>(R.id.home_img_post).setImageURI(post.uri)
      itemView.findViewById<TextView>(R.id.home_txt_caption).text = post.caption
      itemView.findViewById<ImageView>(R.id.home_img_user).setImageURI(post.publisher.photoUri)
      itemView.findViewById<TextView>(R.id.home_text_username).text = post.publisher.name

    }
  }

}