package com.Catch.OrRunOr.fragment

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.Catch.OrRunOr.R
import com.Catch.OrRunOr.databinding.FragmentMainBinding
import com.Catch.OrRunOr.utils.*
import com.Catch.OrRunOr.utils.Constants.BEST_SCORE
import com.Catch.OrRunOr.utils.Constants.EN
import com.Catch.OrRunOr.utils.Constants.KEY_LANGUAGE
import com.Catch.OrRunOr.utils.Constants.KEY_RATING
import com.Catch.OrRunOr.utils.Constants.KEY_VIBRATION



class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding by viewBinding { FragmentMainBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requireActivity().window.statusBarColor = requireActivity().getColor(R.color.menu)
            }
        }

        if (getString(KEY_RATING) == "") {
            saveString(KEY_RATING, "0")
        }

        if (getString(KEY_LANGUAGE) == "") {
            saveString(KEY_LANGUAGE, EN)
        }

        if (getString(BEST_SCORE) == "") {
            saveString(BEST_SCORE, "0")
        }



        with(binding) {
            btnStart.setOnClickListener {
                if (getBoolean(KEY_VIBRATION)) {
                    vibrator()
                    findNavController().navigate(R.id.action_mainFragment_to_startFragment)
                } else {
                    findNavController().navigate(R.id.action_mainFragment_to_startFragment)
                }

            }

            btnSettings.setOnClickListener {
                if (getBoolean(KEY_VIBRATION)) {
                    vibrator()
                    findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
                } else {
                    findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
                }

            }

            btnFeedback.setOnClickListener {
                if (getBoolean(KEY_VIBRATION)) {
                    vibrator()
                    findNavController().navigate(R.id.action_mainFragment_to_feedBackFragment)
                } else {
                    findNavController().navigate(R.id.action_mainFragment_to_feedBackFragment)

                }
            }
        }


    }

}