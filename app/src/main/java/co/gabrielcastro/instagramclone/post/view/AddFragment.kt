package co.gabrielcastro.instagramclone.post.view

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import co.gabrielcastro.instagramclone.databinding.FragmentAddBinding
import co.gabrielcastro.instagramclone.R
import co.gabrielcastro.instagramclone.add.view.AddActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AddFragment : Fragment(R.layout.fragment_add,) {

  private var binding: FragmentAddBinding? = null
  private var addListener: AddListener? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setFragmentResultListener("takePhotoKey") {requestKey, bundle ->
      val uri = bundle.getParcelable<Uri>("uri")
      uri?.let {
        val intent = Intent(requireContext(), AddActivity::class.java)
        intent.putExtra("photoUri", uri)
        addActivityResult.launch(intent)
      }
    }
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is AddListener) {
      addListener = context
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding = FragmentAddBinding.bind(view)
    if (savedInstanceState == null) {
      setupViews()
    }
  }

  private fun setupViews() {
    val tabLayout = binding?.addTablelayout
    val viewPager = binding?.addViewpager
    val adapter = AddViewPageAdapter(requireActivity())
    viewPager?.adapter = adapter

    if (tabLayout != null && viewPager != null) {
      tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
          if (tab?.text == getString(adapter.tabs[0])) {
            startCamera()
          }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
        }
      })
      TabLayoutMediator(tabLayout, viewPager) { tab, position ->
        tab.text = getString(adapter.tabs[position])
      }.attach()
    }

    if (allPermissionsGranted()) {
      startCamera()
    } else {
      getPermission.launch(REQUIRED_PERMISSION)
    }
  }

  private fun startCamera() {
    setFragmentResult("cameraKey", bundleOf("startCamera" to true))
  }

  private val addActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
    if (it.resultCode == Activity.RESULT_OK) {
      addListener?.onPostCreated()
    }
  }

  private val getPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { granted ->
    if (allPermissionsGranted()) {
      startCamera()
    } else {
      Toast.makeText(requireContext(), R.string.permission_camera_denied, Toast.LENGTH_LONG).show()
    }
  }

  private fun allPermissionsGranted() =
    ContextCompat.checkSelfPermission(requireContext(), REQUIRED_PERMISSION[0]) == PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(requireContext(), REQUIRED_PERMISSION[1]) == PackageManager.PERMISSION_GRANTED

  interface AddListener {
    fun onPostCreated()
  }

  companion object {
    private val REQUIRED_PERMISSION = arrayOf( Manifest.permission.CAMERA, Manifest.permission.READ_MEDIA_IMAGES )
  }


}