package com.theideal.goride.ui.driver.profile.items

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.theideal.goride.R
import com.theideal.goride.databinding.ActivityProfileBinding
import timber.log.Timber

class ProfileActivity : AppCompatActivity() {
    private val binding by lazy { ActivityProfileBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_profile) as NavHostFragment
        val navController = navHostFragment.navController
        val fragment = intent.getStringExtra("Fragment")
        Timber.d("Fragment: $fragment")
        when (fragment) {
            "DestinationPreferences" -> {
                navController.navigate(R.id.action_accountInformationFragment_to_destinationPreferencesFragment)
            }
            else -> {

            }
        }
    }
}