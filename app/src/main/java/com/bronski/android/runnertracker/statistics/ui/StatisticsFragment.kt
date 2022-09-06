package com.bronski.android.runnertracker.statistics.ui

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.bronski.android.runnertracker.R
import com.bronski.android.runnertracker.core.ui.BaseFragment
import com.bronski.android.runnertracker.core.utils.CustomMarkerView
import com.bronski.android.runnertracker.core.utils.TrackingUtility
import com.bronski.android.runnertracker.databinding.FragmentStatisticsBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.round

@AndroidEntryPoint
class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>() {

    private val viewModel by viewModels<StatisticsViewModel>()

    override fun getViewBinding() = FragmentStatisticsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()
        setupBarChart()
    }

    private fun setupBarChart() {

        binding.barChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawLabels(false)
            setDrawGridLines(false)
        }
        binding.barChart.axisLeft.apply {
            if (isDarkThemeEnabled()) {
                textColor = Color.WHITE
            }
            setDrawGridLines(false)
        }
        binding.barChart.axisRight.apply {
            if (isDarkThemeEnabled()) {
                textColor = Color.WHITE
            }
            setDrawGridLines(false)
        }
        binding.barChart.apply {
            description.text = "Avg Speed Over Time"
            legend.isEnabled = false
        }
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
                val totalDistanceString = "$totalDistance" + requireContext().getString(R.string._0km)
                binding.totalDistanceTextView.text = totalDistanceString
            }
        }

        viewModel.totalAverageSpeed.observe(viewLifecycleOwner) {
            it?.let {
                val avgSpeed = round(it * 10f) / 10f
                val avgSpeedString = "$avgSpeed" + requireContext().getString(R.string._0km_h)
                binding.averageSpeedTextView.text = avgSpeedString
            }
        }

        viewModel.totalCaloriesBurned.observe(viewLifecycleOwner) {
            it?.let {
                val totalCalories = "$it" + requireContext().getString(R.string._0kcal)
                binding.totalCaloriesTextView?.text = totalCalories
            }
        }

        viewModel.runsSortedByDate.observe(viewLifecycleOwner) {
            it?.let {
                val allAvgSpeeds =
                    it.indices.map { i -> BarEntry(i.toFloat(), it[i].averageSpeedInKmh) }
                val barDataSet = BarDataSet(allAvgSpeeds, "Average Speed Over Time").apply {
                    valueTextColor = Color.WHITE
                    color = ContextCompat.getColor(requireContext(), R.color.color_accent)
                }
                binding.barChart.data = BarData(barDataSet)
                binding.barChart.marker =
                    CustomMarkerView(it.reversed(), requireContext(), R.layout.marker_view)
                binding.barChart.invalidate()
            }
        }
    }

    private fun isDarkThemeEnabled(): Boolean {
        val themeApp = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_YES
        var isEnabled = false
        when (themeApp) {
            Configuration.UI_MODE_NIGHT_YES -> {
                isEnabled = true
            }
        }
        return isEnabled
    }
}