package com.Catch.OrRunOr.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.Catch.OrRunOr.R
import com.Catch.OrRunOr.databinding.FragmentSettingsBinding
import com.Catch.OrRunOr.utils.*
import com.Catch.OrRunOr.utils.Constants.EN
import com.Catch.OrRunOr.utils.Constants.KEY_LANGUAGE
import com.Catch.OrRunOr.utils.Constants.KEY_SOUND
import com.Catch.OrRunOr.utils.Constants.KEY_VIBRATION
import com.Catch.OrRunOr.utils.Constants.RU
import dev.b3nedikt.app_locale.AppLocale
import dev.b3nedikt.reword.Reword
import java.util.*


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding by viewBinding { FragmentSettingsBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manageLangIcon(getString(KEY_LANGUAGE))
        manageSoundIcon(getBoolean(KEY_SOUND))
        manageVibrationIcon(getBoolean(KEY_VIBRATION))


        with(binding) {

                binding.btnBack.setOnClickListener {
                    if (getBoolean(KEY_VIBRATION)) {
                        vibrator()
                        requireActivity().onBackPressed()
                    } else {
                        requireActivity().onBackPressed()
                    }

                }

                binding.sound.setOnClickListener {
                    if (getBoolean(KEY_VIBRATION)) {
                        vibrator()
                        if (getBoolean(KEY_SOUND)) {
                            saveBoolean(KEY_SOUND, false)
                            requireActivity().stopService(Intent(requireContext(), SoundService::class.java))
                            manageSoundIcon(false)
                        } else {
                            saveBoolean(KEY_SOUND, true)
                            requireActivity().startService(Intent(requireContext(), SoundService::class.java))
                            manageSoundIcon(true)
                        }
                    } else {
                        if (getBoolean(KEY_SOUND)) {
                            saveBoolean(KEY_SOUND, false)
                            requireActivity().stopService(Intent(requireContext(), SoundService::class.java))
                            manageSoundIcon(false)
                        } else {
                            saveBoolean(KEY_SOUND, true)
                            requireActivity().startService(Intent(requireContext(), SoundService::class.java))
                            manageSoundIcon(true)
                        }
                    }


                }

                binding.vibration.setOnClickListener {
                    if (getBoolean(KEY_VIBRATION)) {
                        vibrator()
                        manageVibrationIcon(getBoolean(KEY_VIBRATION))
                        if (getBoolean(KEY_VIBRATION)) {
                            vibrator()
                            saveBoolean(KEY_VIBRATION, false)
                            manageVibrationIcon(false)
                        } else if (!getBoolean(KEY_VIBRATION)) {
                            saveBoolean(KEY_VIBRATION, true)
                            manageVibrationIcon(true)
                        }
                    } else {
                        manageVibrationIcon(getBoolean(KEY_VIBRATION))
                        if (getBoolean(KEY_VIBRATION)) {
                            vibrator()
                            saveBoolean(KEY_VIBRATION, false)
                            manageVibrationIcon(false)
                        } else if (!getBoolean(KEY_VIBRATION)) {
                            saveBoolean(KEY_VIBRATION, true)
                            manageVibrationIcon(true)
                        }
                    }

                }

                binding.bntEng.setOnClickListener {
                    if (getBoolean(KEY_VIBRATION)) {
                        vibrator()
                        manageLangIcon(EN)
                        saveString(KEY_LANGUAGE, EN)
                        AppLocale.desiredLocale = Locale(EN)
                        Reword.reword(binding.root)
                    } else {
                        manageLangIcon(EN)
                        saveString(KEY_LANGUAGE, EN)
                        AppLocale.desiredLocale = Locale(EN)
                        Reword.reword(binding.root)
                    }

                }

                binding.btnRu.setOnClickListener {
                    if (getBoolean(KEY_VIBRATION)) {
                        vibrator()
                        manageLangIcon(RU)
                        saveString(KEY_LANGUAGE, RU)
                        AppLocale.desiredLocale = Locale(RU)
                        Reword.reword(binding.root)
                    } else {
                        manageLangIcon(RU)
                        saveString(KEY_LANGUAGE, RU)
                        AppLocale.desiredLocale = Locale(RU)
                        Reword.reword(binding.root)
                    }

                }

            }

    }

    private fun manageVibrationIcon(boolean: Boolean) {
        if (boolean) {
            binding.vibration.setImageResource(R.drawable.ic_switch_on)
        } else {
            binding.vibration.setImageResource(R.drawable.img_switch_off)
        }
    }

    private fun manageSoundIcon(boolean: Boolean) {
        if (boolean) {
            binding.sound.setImageResource(R.drawable.ic_switch_on)
        } else {
            binding.sound.setImageResource(R.drawable.img_switch_off)
        }
    }

    private fun manageLangIcon(string: String) {
        if (string == RU) {
            binding.bntEng.setImageResource(R.drawable.img_flag_en_off)
            binding.btnRu.setImageResource(R.drawable.img_flag_ru)
        } else {
            binding.bntEng.setImageResource(R.drawable.img_flag_eng)
            binding.btnRu.setImageResource(R.drawable.img_flag_ru_off)
        }
    }

}