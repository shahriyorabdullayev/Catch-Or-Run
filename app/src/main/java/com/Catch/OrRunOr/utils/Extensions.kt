package com.Catch.OrRunOr.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.Catch.OrRunOr.utils.Constants.KEY_VIBRATION


fun Fragment.navigateFragment(id: Int, isVibrate: Boolean) {
    if (isVibrate) {
        vibrator()
        findNavController().navigate(id)
    } else {
        findNavController().navigate(id)
    }
}

fun Fragment.vibrator() {
    if (getBoolean(KEY_VIBRATION)) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                this.requireContext()
                    .getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            val vib = vibratorManager.defaultVibrator
            vib.vibrate(VibrationEffect.createOneShot(100, 1))
        } else {
            val vib = this.requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vib.vibrate(70)
        }
    }
}

fun Fragment.saveBoolean(key: String, value: Boolean) {
    SharedPref(this.requireContext()).saveBoolean(key, value)
}

fun Fragment.getBoolean(key: String): Boolean {
    return SharedPref(this.requireContext()).getBoolean(key)
}

fun Fragment.saveString(key: String, value: String) {
    SharedPref(this.requireContext()).saveString(key, value)
}

fun Fragment.getString(key: String): String {
    val language = SharedPref(this.requireContext()).getString(key)
    if (language != null) {
        return language
    }
    return ""
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}



