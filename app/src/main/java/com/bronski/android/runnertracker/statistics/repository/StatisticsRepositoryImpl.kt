package com.bronski.android.runnertracker.statistics.repository

import androidx.lifecycle.LiveData
import com.bronski.android.runnertracker.core.data.room.RunDao
import com.bronski.android.runnertracker.core.data.room.RunEntity
import javax.inject.Inject

class StatisticsRepositoryImpl @Inject constructor(
    private val runDao: RunDao
) : IStatisticsRepository {

    override fun getTotalTimeInMillis(): LiveData<Long> =
        runDao.getTotalTimeInMillis()

    override fun getTotalCaloriesBurned(): LiveData<Int> =
        runDao.getTotalCaloriesBurned()

    override fun getTotalDistanceInMeters(): LiveData<Int> =
        runDao.getTotalDistanceInMeters()

    override fun getTotalAverageSpeedInKmh(): LiveData<Float> =
        runDao.getTotalAverageSpeedInKmh()

    override fun getAllRunsSortedByDate(): LiveData<List<RunEntity>> =
        runDao.getAllRunsSortedByDate()
}