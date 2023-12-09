package com.dicoding.courseschedule.ui.setting

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NightMode

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference

        val themePreference = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        themePreference?.setOnPreferenceChangeListener { _, newValue ->
            val nightMode = when (newValue) {
                "on" -> NightMode.OFF
                "off" -> NightMode.ON
                else -> NightMode.AUTO
            }
            updateTheme(nightMode.ordinal)
            true
        }


        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference

        val perm1 = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.VIBRATE
        )

        val perm2 = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.RECEIVE_BOOT_COMPLETED
        )

        val perm3 = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.WAKE_LOCK
        )
        val perm4 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.POST_NOTIFICATIONS
            )
        } else {
            0
        }

        if (perm1 == 0 &&
            perm2 == 0 &&
            perm3 == 0 &&
            perm4 == 0) {

            val prefNotification = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
            val dailyReminder = DailyReminder()
            prefNotification?.setOnPreferenceChangeListener { _, newValue ->
                if (newValue == true) {
                    dailyReminder.setDailyReminder(requireContext())
                } else {
                    dailyReminder.cancelAlarm(requireContext())
                }
                true
            }
        } else {
            Toast.makeText(requireContext(), "Please allow permission to use this feature", Toast.LENGTH_SHORT).show()
            // ask permission to accept
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.RECEIVE_BOOT_COMPLETED,
                    android.Manifest.permission.VIBRATE,
                    android.Manifest.permission.WAKE_LOCK,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ),
                1
            )
        }
        val prefNotification = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))

        val dailyReminder = DailyReminder()

        prefNotification?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue == true) {
                dailyReminder.setDailyReminder(requireContext())
            } else {
                dailyReminder.cancelAlarm(requireContext())
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
            AppCompatDelegate.setDefaultNightMode(nightMode)
            requireActivity().recreate()
            return true
        }
    }
