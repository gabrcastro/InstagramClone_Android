package co.gabrielcastro.instagramclone.post.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import co.gabrielcastro.instagramclone.R

class AddViewPageAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

  val tabs = arrayOf(R.string.photo, R.string.gallery)

  override fun getItemCount() = tabs.size

  override fun createFragment(position: Int): Fragment {
    return when(tabs[position]) {
      R.string.photo -> CameraFragment()
      R.string.gallery -> GalleryFragment()
      else -> throw IllegalArgumentException("Fragment not found")
    }
  }
}