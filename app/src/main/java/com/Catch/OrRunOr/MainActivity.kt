package com.Catch.OrRunOr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.ViewPumpAppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.Catch.OrRunOr.databinding.ActivityMainBinding
import com.Catch.OrRunOr.utils.Constants.KEY_SOUND
import com.Catch.OrRunOr.utils.SharedPref
import com.Catch.OrRunOr.utils.SoundService
import dev.b3nedikt.app_locale.AppLocale

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (SharedPref(this).getBoolean(KEY_SOUND)) {
            startService(Intent(this, SoundService::class.java))
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController



    }

    private val appCompatDelegate: AppCompatDelegate by lazy {
        ViewPumpAppCompatDelegate(
            baseDelegate = super.getDelegate(),
            baseContext = this,
            wrapContext = AppLocale::wrap
        )
    }

    override fun getDelegate(): AppCompatDelegate {
        return appCompatDelegate
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, SoundService::class.java))
    }


}