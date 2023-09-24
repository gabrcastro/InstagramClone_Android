package co.gabrielcastro.instagramclone.profile.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import co.gabrielcastro.instagramclone.R
import co.gabrielcastro.instagramclone.common.base.BaseFragment
import co.gabrielcastro.instagramclone.common.base.DependencyInjector
import co.gabrielcastro.instagramclone.common.model.Post
import co.gabrielcastro.instagramclone.common.model.UserAuth
import co.gabrielcastro.instagramclone.databinding.FragmentProfileBinding
import co.gabrielcastro.instagramclone.profile.Profile
import co.gabrielcastro.instagramclone.profile.presenter.ProfilePresenter
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileFragment : BaseFragment<FragmentProfileBinding, Profile.Presenter>(
	R.layout.fragment_profile,
	FragmentProfileBinding::bind
), Profile.View, BottomNavigationView.OnNavigationItemSelectedListener {

	override lateinit var presenter: Profile.Presenter
	private val adapter = PostAdapter()
	override fun setupPresenter() {
		val repository =  DependencyInjector.profileRepository()
		 presenter = ProfilePresenter(this, repository)
	}

	override fun setupViews(savedInstanceState: Bundle?) {
		binding?.profileRv?.layoutManager = GridLayoutManager(requireContext(), 3)
		binding?.profileRv?.adapter = adapter
		binding?.profileNavTabs?.setOnNavigationItemSelectedListener(this)
		if (savedInstanceState == null ) {
			presenter.fetchUserProfile()
		}
	}

	override fun getMenu(): Int {
		return R.menu.menu_profile
	}

	override fun showProgress(enabled: Boolean) {
		binding?.profileProgress?.visibility = if (enabled) View.VISIBLE else View.GONE
	}

	override fun displayUserProfile(userAuth: UserAuth) {
		binding?.profileTxtPostsCount?.text = userAuth.postCount.toString()
		binding?.profileTxtFollowingCount?.text = userAuth.followingCount.toString()
		binding?.profileTxtFollowersCount?.text = userAuth.followersCount.toString()
		binding?.profileTxtUsername?.text = userAuth.name
		binding?.profileTxtBio?.text = "TODO"
		binding?.profileImgIcon?.setImageURI(userAuth.photoUri)
		presenter.fetchUserPosts()
	}

	override fun displayRequestFailure(message: String) {
		Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
	}

	override fun displayEmptyPosts() {
		binding?.profileTxtEmpty?.visibility = View.VISIBLE
		binding?.profileRv?.visibility = View.GONE
	}

	override fun displayFullPosts(posts: List<Post>) {
		binding?.profileTxtEmpty?.visibility = View.GONE
		binding?.profileRv?.visibility = View.VISIBLE

		adapter.items = posts
		adapter.notifyDataSetChanged()
	}

	override fun onNavigationItemSelected(item: MenuItem): Boolean {
		when(item.itemId) {
			R.id.menu_profile_grid -> {
				binding?.profileRv?.layoutManager = GridLayoutManager(requireContext(), 3)
			}
			R.id.menu_profile_list -> {
				binding?.profileRv?.layoutManager = LinearLayoutManager(requireContext())
			}
		}

		return true
	}
}