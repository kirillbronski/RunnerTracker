package com.bronski.android.runnertracker.statistics.ui

import androidx.lifecycle.ViewModel
import com.bronski.android.runnertracker.statistics.repository.IStatisticsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val statisticsRepository: IStatisticsRepository,
) : ViewModel() {

    val totalTimeRun = statisticsRepository.getTotalTimeInMillis()
    val totalCaloriesBurned = statisticsRepository.getTotalCaloriesBurned()
    val totalAverageSpeed = statisticsRepository.getTotalAverageSpeedInKmh()
    val totalDistance = statisticsRepository.getTotalDistanceInMeters()

    val runsSortedByDate = statisticsRepository.getAllRunsSortedByDate()

}