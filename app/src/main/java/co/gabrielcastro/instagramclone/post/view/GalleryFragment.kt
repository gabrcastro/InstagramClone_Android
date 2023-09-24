package co.gabrielcastro.instagramclone.post.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import co.gabrielcastro.instagramclone.R
import co.gabrielcastro.instagramclone.common.base.BaseFragment
import co.gabrielcastro.instagramclone.common.base.DependencyInjector
import co.gabrielcastro.instagramclone.databinding.FragmentGalleryBinding
import co.gabrielcastro.instagramclone.post.Post
import co.gabrielcastro.instagramclone.post.presenter.PostPresenter

class GalleryFragment : BaseFragment<FragmentGalleryBinding, Post.Presenter>(
    R.layout.fragment_gallery,
    FragmentGalleryBinding::bind
), Post.View {

    override lateinit var presenter: Post.Presenter

    private val adapter = PictureAdapter() {
        binding?.galleryImgSelected?.setImageURI(it)
        binding?.galleryNested?.smoothScrollTo(0, 0)

        presenter.selectUri(it)
    }

    override fun setupPresenter() {
        presenter = PostPresenter(this, DependencyInjector.postRepository(requireContext()))
    }

    override fun getMenu(): Int? = R.menu.menu_send

    override fun setupViews(savedInstanceState: Bundle?) {
        binding?.galleryRv?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.galleryRv?.adapter = adapter

        presenter.fetchPictures()
    }

    override fun showProgress(enabled: Boolean) {
        binding?.galleryProgress?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayFullPictures(posts: List<Uri>) {
        binding?.galleryTxtEmpty?.visibility = View.GONE
        binding?.galleryRv?.visibility = View.VISIBLE
        adapter.items = posts
        adapter.notifyDataSetChanged()

        binding?.galleryImgSelected?.setImageURI(posts.first())
        binding?.galleryNested?.smoothScrollTo(0, 0)

        presenter.selectUri(posts.first())
    }

    override fun displayEmptyPictures() {
        binding?.galleryTxtEmpty?.visibility = View.VISIBLE
        binding?.galleryRv?.visibility = View.GONE
    }

    override fun displayRequestFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_send -> {
                setFragmentResult("takePhotoKey", bundleOf("uri" to presenter.getSelectedUri()))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}