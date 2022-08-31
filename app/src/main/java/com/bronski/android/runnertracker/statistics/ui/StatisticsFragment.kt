package com.bronski.android.runnertracker.statistics.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bronski.android.runnertracker.core.ui.BaseFragment
import com.bronski.android.runnertracker.core.utils.TrackingUtility
import com.bronski.android.runnertracker.databinding.FragmentStatisticsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.round

@AndroidEntryPoint
class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>() {

    private val viewModel by viewModels<StatisticsViewModel>()

    override fun getViewBinding() = FragmentStatisticsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()
    }

    private fun subscribeToObservers() {

        viewModel.totalTimeRun.observe(viewLifecycleOwner) {
            it?.let {
                val totalTimeRun = TrackingUtility.getFormattedStopWatchTime(it)
                binding.totalTimeTextView.text = totalTimeRun
            }
        }

        viewModel.totalDistance.observe(viewLifecycleOwner) {
            it?.let {
                val km = it / 1000f
                val totalDistance = round(km * 10f) / 10f
                val totalDistanceString = "${totalDistance}km"
                binding.totalDistanceTextView.text = totalDistanceString
            }
        }

        viewModel.totalAverageSpeed.observe(viewLifecycleOwner) {
            it?.let {
                val avgSpeed = round(it * 10f) / 10f
                val avgSpeedString = "${avgSpeed}km/h"
                binding.averageSpeedTextView.text = avgSpeedString
            }
        }

        viewModel.totalCaloriesBurned.observe(viewLifecycleOwner) {
            it?.let {
                val totalCalories = "${it}kcal"
                binding.totalCaloriesTextView.text = totalCalories
            }
        }


    }

}