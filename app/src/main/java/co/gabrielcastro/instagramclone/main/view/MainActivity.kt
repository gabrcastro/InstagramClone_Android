package co.gabrielcastro.instagramclone.main.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import co.gabrielcastro.instagramclone.R
import co.gabrielcastro.instagramclone.camera.view.CameraFragment
import co.gabrielcastro.instagramclone.common.extension.replaceFragment
import co.gabrielcastro.instagramclone.databinding.ActivityMainBinding
import co.gabrielcastro.instagramclone.home.view.HomeFragment
import co.gabrielcastro.instagramclone.profile.view.ProfileFragment
import co.gabrielcastro.instagramclone.search.view.SearchFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

	private lateinit var binding: ActivityMainBinding
	private lateinit var homeFragment: Fragment
	private lateinit var searchFragment: Fragment
	private lateinit var cameraFragment: Fragment
	private lateinit var profileFragment: Fragment
	private var currentFragment: Fragment? = null

	private lateinit var fragmentSavedState: HashMap<String, Fragment.SavedState?>

	@RequiresApi(Build.VERSION_CODES.R)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			window.insetsController?.setSystemBarsAppearance(
				WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
				WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
			)
			window.statusBarColor = ContextCompat.getColor(this, R.color.gray)
		}

		setSupportActionBar(binding.mainToolbar)
		supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_insta_camera)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.title = ""

		if (savedInstanceState == null) {
			fragmentSavedState = HashMap()
		} else {
			savedInstanceState.getSerializable("fragmentState") as HashMap<String, Fragment.SavedState?>
		}

//		homeFragment = HomeFragment()
//		searchFragment = SearchFragment()
//		cameraFragment = CameraFragment()
//		profileFragment = ProfileFragment()

//		currentFragment = homeFragment

//		supportFragmentManager.beginTransaction().apply {
//			add(R.id.main_fragment, profileFragment, "3").hide(profileFragment)
//			add(R.id.main_fragment, cameraFragment, "2").hide(cameraFragment)
//			add(R.id.main_fragment, searchFragment, "1").hide(searchFragment)
//			add(R.id.main_fragment, homeFragment, "0")
//			commit() // fazendo o commit de tods os fragmentos de uma vez
//		}

		binding.mainBottomNav.setOnNavigationItemSelectedListener(this)
		binding.mainBottomNav.selectedItemId = R.id.menu_bottom_home
	}

	private fun setScrollToolbarEnabled(enabled: Boolean) {
		val params = binding.mainToolbar.layoutParams as AppBarLayout.LayoutParams
		val coordinatorParams = binding.mainAppbar.layoutParams as CoordinatorLayout.LayoutParams

		if (enabled) {
			params.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
			coordinatorParams.behavior = AppBarLayout.Behavior()
		} else {
			params.scrollFlags = 0
			coordinatorParams.behavior = null
		}

		binding.mainAppbar.layoutParams = coordinatorParams
	}

	// disparado toda vez que trocar e precisar armazenar um novo estado de fragmento
	override fun onSaveInstanceState(outState: Bundle) {
		outState.putSerializable("fragmentState", fragmentSavedState)
		super.onSaveInstanceState(outState)
	}

	override fun onNavigationItemSelected(item: MenuItem): Boolean {
		var scrollToolbarEnabled = false

		val newFragment: Fragment? = when(item.itemId) {
			R.id.menu_bottom_home -> {
				HomeFragment()
			}
			R.id.menu_bottom_search -> {
				SearchFragment()
			}
			R.id.menu_bottom_add -> {
				CameraFragment()
			}
			R.id.menu_bottom_profile -> {
				ProfileFragment()
			}

			else -> null
		}

		val currFragment = supportFragmentManager.findFragmentById(R.id.main_fragment)
		val fragmentTag = newFragment?.javaClass?.simpleName
		if (!currFragment?.tag.equals(fragmentTag)) {
			currFragment?.let {
				fragmentSavedState.put(
					it.tag!!,
					supportFragmentManager.saveFragmentInstanceState(it)
				)
			}
		}

		newFragment?.setInitialSavedState(fragmentSavedState[fragmentTag])
		newFragment?.let {
			supportFragmentManager.beginTransaction()
				.replace(R.id.main_fragment, it, fragmentTag)
				.addToBackStack(fragmentTag)
				.commit()
		}

//		when (item.itemId) {
//			R.id.menu_bottom_home -> {
//				if (currentFragment == homeFragment) return false
//				supportFragmentManager.beginTransaction().hide(currentFragment).show(homeFragment).commit()
//				currentFragment = homeFragment
//
//			}
//			R.id.menu_bottom_search -> {
//				if (currentFragment == searchFragment) return false
//				supportFragmentManager.beginTransaction().hide(currentFragment).show(searchFragment).commit()
//
//				currentFragment = searchFragment
//			}
//			R.id.menu_bottom_add -> {
//				if (currentFragment == cameraFragment) return false
//				supportFragmentManager.beginTransaction().hide(currentFragment).show(cameraFragment).commit()
//
//				currentFragment = cameraFragment
//				}
//			R.id.menu_bottom_profile -> {
//				if (currentFragment == profileFragment) return false
//				supportFragmentManager.beginTransaction().hide(currentFragment).show(profileFragment).commit()
//
//				currentFragment = profileFragment
//				scrollToolbarEnabled = true
//			}
//		}

		setScrollToolbarEnabled(scrollToolbarEnabled)

//		currentFragment?.let {
//			replaceFragment(R.id.main_fragment, it)
//		}

		return true
	}
}