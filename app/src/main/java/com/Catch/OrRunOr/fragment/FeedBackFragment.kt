package com.Catch.OrRunOr.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.Catch.OrRunOr.R
import com.Catch.OrRunOr.databinding.FragmentFeedBackBinding
import com.Catch.OrRunOr.utils.*
import com.Catch.OrRunOr.utils.Constants.KEY_RATING
import com.Catch.OrRunOr.utils.Constants.KEY_VIBRATION


class FeedBackFragment : Fragment(R.layout.fragment_feed_back) {

    private val binding by viewBinding { FragmentFeedBackBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ratingBar.rating = getString(KEY_RATING).toFloat()

        binding.btnBack.setOnClickListener {
            if (getBoolean(KEY_VIBRATION)) {
                vibrator()
                requireActivity().onBackPressed()
            } else {
                requireActivity().onBackPressed()
            }

        }

        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, ball, b ->
            if(getBoolean(KEY_VIBRATION)) {
                vibrator()
                Toast.makeText(requireContext(), "Your feedback $ball", Toast.LENGTH_SHORT).show()
                saveString(KEY_RATING, ball.toString())
            } else {
                Toast.makeText(requireContext(), "Your feedback $ball", Toast.LENGTH_SHORT).show()
                saveString(KEY_RATING, ball.toString())
            }

        }

    }

}