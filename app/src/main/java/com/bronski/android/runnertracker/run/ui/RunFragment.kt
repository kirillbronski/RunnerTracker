package com.bronski.android.runnertracker.run.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bronski.android.runnertracker.R
import com.bronski.android.runnertracker.core.ui.BaseFragment
import com.bronski.android.runnertracker.databinding.FragmentRunBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RunFragment : BaseFragment<FragmentRunBinding>() {

    private val viewModel by viewModels<RunViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermission()
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
        }
    }

    private fun onGotPermissionsResult(grantResults: Map<String, Boolean>) {
        grantResults.forEach {
            Timber.d(it.toString())
        }
        if (grantResults.entries.all { it.value }) {
            return
        } else {
            askUserForOpenInAppSettings()
        }
    }

    private fun requestPermission() {
        val permissionsRequestLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
            ::onGotPermissionsResult
        )
        permissionsRequestLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            ))
    }

    private fun askUserForOpenInAppSettings() {
        val appSettingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", activity?.packageName?.toString(), null)
        )

        activity?.packageManager?.resolveActivity(
            appSettingsIntent,
            PackageManager.MATCH_DEFAULT_ONLY
        )?.let {
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.permission_denied)
                .setMessage(R.string.permission_denied_forever_message)
                .setCancelable(false)
                .setPositiveButton("Open") { _, _ ->
                    startActivity(appSettingsIntent)
                }
                .create()
                .show()
        }
    }

    override fun getViewBinding() = FragmentRunBinding.inflate(layoutInflater)
}