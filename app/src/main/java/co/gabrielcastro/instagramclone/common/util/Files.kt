package co.gabrielcastro.instagramclone.common.util

import android.app.Activity
import java.io.File
import co.gabrielcastro.instagramclone.R
import java.text.SimpleDateFormat
import java.util.Locale

object Files {

  private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

  fun generateFile(activity: Activity) : File {
    val mediaDir = activity.externalMediaDirs.firstOrNull()?.let {
      File(it, activity.getString(R.string.app_name)).apply {
        mkdir()
      }
    }

    val outputDir = if (mediaDir != null && mediaDir.exists())
      mediaDir else activity.filesDir

    return File(outputDir, SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis()) + ".jpg")
  }

}