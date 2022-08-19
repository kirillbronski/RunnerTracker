package com.bronski.android.runnertracker.core.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.bronski.android.runnertracker.R
import com.bronski.android.runnertracker.core.utils.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import com.bronski.android.runnertracker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigateToTrackingFragmentIfNeeded(intent)
        setSupportActionBar(binding.toolbar)
        setupNavigation()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onSupportNavigateUp(): Boolean {
        return getNavController().navigateUp() || super.onSupportNavigateUp()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            navigateToTrackingFragmentIfNeeded(intent)
        }
    }

    private fun setupNavigation() {
        NavigationUI.setupActionBarWithNavController(this, getNavController())
        binding.bottomNavigationView.setupWithNavController(getNavController())
        getNavController().addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.settingsFragment, R.id.runFragment, R.id.statisticsFragment ->
                    binding.bottomNavigationView.visibility = View.VISIBLE
                else -> binding.bottomNavigationView.visibility = View.GONE
            }
        }
    }

    private fun getNavController(): NavController {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_main) as NavHostFragment
        return navHostFragment.navController
    }

    private fun navigateToTrackingFragmentIfNeeded(intent: Intent) {
        if (intent.action == ACTION_SHOW_TRACKING_FRAGMENT) {
            getNavController().navigate(R.id.action_global_trackingFragment)
        }
    }
}