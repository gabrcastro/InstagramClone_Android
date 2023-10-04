package co.gabrielcastro.instagramclone.profile.view

import android.content.Context
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
import co.gabrielcastro.instagramclone.common.model.User
import co.gabrielcastro.instagramclone.common.model.UserAuth
import co.gabrielcastro.instagramclone.databinding.FragmentProfileBinding
import co.gabrielcastro.instagramclone.main.LogoutListener
import co.gabrielcastro.instagramclone.profile.Profile
import co.gabrielcastro.instagramclone.profile.presenter.ProfilePresenter
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView


class ProfileFragment : BaseFragment<FragmentProfileBinding, Profile.Presenter>(
	R.layout.fragment_profile,
	FragmentProfileBinding::bind
), Profile.View, BottomNavigationView.OnNavigationItemSelectedListener {

	override lateinit var presenter: Profile.Presenter
	private val adapter = PostAdapter()
	private var uuid: String? = null
	private var logoutListener: LogoutListener? = null
	private var followListener: FollowListener? = null
	override fun onAttach(context: Context) {
		super.onAttach(context)
		if (context is LogoutListener) {
			logoutListener = context
		}
		if (context is FollowListener) {
			followListener = context
		}
	}

	override fun setupPresenter() {
		val repository =  DependencyInjector.profileRepository()
		 presenter = ProfilePresenter(this, repository)
	}

	override fun setupViews(savedInstanceState: Bundle?) {
		uuid = arguments?.getString(KEY_USER_ID)

		binding?.profileRv?.layoutManager = GridLayoutManager(requireContext(), 3)
		binding?.profileRv?.adapter = adapter
		binding?.profileNavTabs?.setOnNavigationItemSelectedListener(this)

		binding?.profileButtonEditProfile?.setOnClickListener {
			if (it.tag == true) {
				binding?.profileButtonEditProfile?.text = getString(R.string.follow)
				binding?.profileButtonEditProfile?.tag = false
				uuid?.let { it1 -> presenter.followUser(it1, false) }

			} else if (it.tag == false) {
				binding?.profileButtonEditProfile?.text = getString(R.string.unfollow)
				binding?.profileButtonEditProfile?.tag = true
				uuid?.let { it1 -> presenter.followUser(it1, true) }
			}
		}

		presenter.fetchUserProfile(uuid)
	}

	override fun getMenu(): Int {
		return R.menu.menu_profile
	}

	override fun showProgress(enabled: Boolean) {
		binding?.profileProgress?.visibility = if (enabled) View.VISIBLE else View.GONE
	}

	override fun displayUserProfile(user: Pair<User, Boolean?>) {

		val (userAuth, following) = user

		binding?.profileTxtPostsCount?.text = userAuth.postCount.toString()
		binding?.profileTxtFollowingCount?.text = userAuth.following.toString()
		binding?.profileTxtFollowersCount?.text = userAuth.followers.toString()
		binding?.profileTxtUsername?.text = userAuth.name
		binding?.profileTxtBio?.text = "TODO"

		binding?.let {
			Glide.with(requireContext()).load(userAuth.photoUrl).into(it.profileImgIcon)
			//binding?.profileImgIcon?.setImageURI(userAuth.photoUrl)
		}

		binding?.profileButtonEditProfile?.text = when(following) {
			null -> getString(R.string.edit_profile)
			true -> getString(R.string.unfollow)
			false -> getString(R.string.follow)
		}

		binding?.profileButtonEditProfile?.tag = following

		presenter.fetchUserPosts(uuid)
	}

	override fun displayRequestFailure(message: String) {
		Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
	}

	override fun followUpdated() {
		followListener?.followUpdated()
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

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			R.id.menu_logout -> {
				logoutListener?.logout()
				return true
			}
		}
		return super.onOptionsItemSelected(item)
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

	interface FollowListener {
		fun followUpdated()
	}
	companion object {
		const val KEY_USER_ID = "key_user_id"
	}
}