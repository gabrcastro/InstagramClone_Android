package co.gabrielcastro.instagramclone.common.extension

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import co.gabrielcastro.instagramclone.R

fun Activity.hideKeyBoard() {
	val imm: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
	var view: View? = currentFocus
	if (view == null) {
		view = View(this)
	}
	imm.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun Activity.animationEnd(callback: () -> Unit) : AnimatorListenerAdapter {
	return object : AnimatorListenerAdapter() {
		override fun onAnimationEnd(animation: Animator) {
			callback.invoke()
		}
	}
}

fun AppCompatActivity.replaceFragment(@IdRes id: Int, fragment: Fragment) {
	if (supportFragmentManager.findFragmentById(id) == null) {
		supportFragmentManager.beginTransaction().apply {
			add(id, fragment, fragment.javaClass.simpleName)
			commit()
		}
	} else {
		supportFragmentManager.beginTransaction().apply {
			replace(id, fragment, fragment.javaClass.simpleName)
			addToBackStack(null)
			commit()
		}
	}
}