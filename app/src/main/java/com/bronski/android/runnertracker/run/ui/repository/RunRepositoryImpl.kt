package com.bronski.android.runnertracker.run.ui.repository

import androidx.lifecycle.LiveData
import com.bronski.android.runnertracker.core.data.room.RunDao
import com.bronski.android.runnertracker.core.data.room.RunEntity
import javax.inject.Inject

class RunRepositoryImpl @Inject constructor(
    private val runDao: RunDao,
) : IRunRepository {

    override fun getAllRunsSortedByDate(): LiveData<List<RunEntity>> =
        runDao.getAllRunsSortedByDate()

    override fun getAllRunsSortedByTimeInMillis(): LiveData<List<RunEntity>> =
        runDao.getAllRunsSortedByTimeInMillis()

    override fun getAllRunsSortedByCaloriesBurned(): LiveData<List<RunEntity>> =
        runDao.getAllRunsSortedByCaloriesBurned()

    override fun getAllRunsSortedByDistanceInMeters(): LiveData<List<RunEntity>> =
        runDao.getAllRunsSortedByDistanceInMeters()

    override fun getAllRunsSortedByAverageSpeedInKmh(): LiveData<List<RunEntity>> =
        runDao.getAllRunsSortedByAverageSpeedInKmh()
}
