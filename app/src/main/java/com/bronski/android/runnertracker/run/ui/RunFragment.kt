package com.bronski.android.runnertracker.run.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bronski.android.runnertracker.R
import com.bronski.android.runnertracker.core.data.room.RunEntity
import com.bronski.android.runnertracker.core.ui.BaseFragment
import com.bronski.android.runnertracker.core.utils.RecyclerItemListener
import com.bronski.android.runnertracker.core.utils.SortType
import com.bronski.android.runnertracker.databinding.FragmentRunBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RunFragment : BaseFragment<FragmentRunBinding>() {

    private val viewModel by viewModels<RunViewModel>()

    private val recyclerItemListener = object : RecyclerItemListener {
        override fun onItemClick(itemRun: RunEntity) {
            Toast.makeText(requireContext(), "${itemRun.id}", Toast.LENGTH_SHORT).show()
        }
    }

    private val runAdapter = RunAdapter(listener = recyclerItemListener)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermission()
        setupAdapter()
        observeToRunList()
        setupSortType()
        setupSpinnerFilter()
        binding.fab.setOnClickListener {
            navigateToTrackingFragment()
        }


    }

    private fun navigateToTrackingFragment() {
        findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
    }

    private fun setupSpinnerFilter() {
        binding.filterSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> viewModel.sortRuns(SortType.DATE)
                    1 -> viewModel.sortRuns(SortType.RUNNING_TIME)
                    2 -> viewModel.sortRuns(SortType.DISTANCE)
                    3 -> viewModel.sortRuns(SortType.AVG_SPEED)
                    4 -> viewModel.sortRuns(SortType.CALORIES_BURNED)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun setupSortType() {
        when (viewModel.sortType) {
            SortType.DATE -> binding.filterSpinner.setSelection(0)
            SortType.RUNNING_TIME -> binding.filterSpinner.setSelection(1)
            SortType.DISTANCE -> binding.filterSpinner.setSelection(2)
            SortType.AVG_SPEED -> binding.filterSpinner.setSelection(3)
            SortType.CALORIES_BURNED -> binding.filterSpinner.setSelection(4)
        }
    }

    private fun observeToRunList() {
        viewModel.runs.observe(viewLifecycleOwner) {
            runAdapter.submitList(it)
        }
    }

    private fun setupAdapter() = with(binding) {
        runsRecyclerView.apply {
            adapter = runAdapter
            layoutManager = LinearLayoutManager(requireContext())
            (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
        }
    }

    private fun onGotPermissionsResult(grantResults: Map<String, Boolean>) {
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
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
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