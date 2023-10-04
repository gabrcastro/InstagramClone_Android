package co.gabrielcastro.instagramclone.profile.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import co.gabrielcastro.instagramclone.R
import co.gabrielcastro.instagramclone.common.model.Post
import com.bumptech.glide.Glide

class PostAdapter() : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

	var items: List<Post> = mutableListOf()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
		// layout que vamos criar
		return PostViewHolder(
			LayoutInflater.from(parent.context).inflate(R.layout.item_profile_grid, parent, false)
		)
	}

	override fun getItemCount(): Int {
		// quantidade de items da celula
		return items.size
	}

	override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
		// devolver posicao
		holder.bind(items[position].url)
	}

	class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		fun bind(url: String?) {
			Glide.with(itemView.context).load(url).into(itemView.findViewById(R.id.item_profile_img_grid))
			//itemView.findViewById<ImageView>(R.id.item_profile_img_grid).setImageURI(url)
		}
	}

}