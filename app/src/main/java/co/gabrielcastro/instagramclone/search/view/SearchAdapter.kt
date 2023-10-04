package co.gabrielcastro.instagramclone.search.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.gabrielcastro.instagramclone.R
import co.gabrielcastro.instagramclone.common.model.User
import co.gabrielcastro.instagramclone.common.model.UserAuth
import com.bumptech.glide.Glide

class SearchAdapter(private val itemClick: (String) -> Unit) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    var items: List<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
      // layout que vamos criar
      return SearchViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_user_list, parent, false)
      )
    }
    override fun getItemCount(): Int {
      // quantidade de items da celula
      return items.size
    }
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
      // devolver posicao
      holder.bind(items[position])
    }
  inner class SearchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
      fun bind(user: User) {
        Glide.with(itemView.context).load(user.photoUrl).into(itemView.findViewById(R.id.search_img_user))
        //itemView.findViewById<ImageView>(R.id.search_img_user).setImageURI(user.photoUri)
        itemView.findViewById<TextView>(R.id.search_txt_username).text = user.name
        itemView.setOnClickListener {
          if (user.uuid != null)
            itemClick.invoke(user.uuid)
        }
      }
    }
  }