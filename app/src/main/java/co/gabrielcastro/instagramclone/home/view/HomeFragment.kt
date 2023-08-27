package co.gabrielcastro.instagramclone.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.gabrielcastro.instagramclone.R
import co.gabrielcastro.instagramclone.common.base.BaseFragment
import co.gabrielcastro.instagramclone.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding, Home.Presenter>(
	R.layout.fragment_home,
	FragmentHomeBinding::bind
) {

	override lateinit var presenter: Home.Presenter

	override fun setupPresenter() {
		// TODO : presenter = HomePresenter(this, repository)
	}

	override fun setupViews(savedInstanceState: Bundle?) {
		binding?.homeRv?.layoutManager = LinearLayoutManager(requireContext())
		binding?.homeRv?.adapter = PostAdapter()
	}

	override fun getMenu(): Int? {
		return R.menu.menu_profile
	}

	private class PostAdapter() : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
			// layout que vamos criar
			return PostViewHolder(
				LayoutInflater.from(parent.context).inflate(R.layout.item_post_list, parent, false)
			)
		}

		override fun getItemCount(): Int {
			// quantidade de items da celula
			return 30
		}

		override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
			// devolver posicao
			holder.bind(R.drawable.ic_insta_add)
		}

		private class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
			fun bind(image: Int) {
				itemView.findViewById<ImageView>(R.id.home_img_post).setImageResource(image)
			}
		}

	}

}