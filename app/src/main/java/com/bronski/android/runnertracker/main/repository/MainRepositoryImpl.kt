package com.bronski.android.runnertracker.main.repository

import com.bronski.android.runnertracker.core.data.room.RunDao
import com.bronski.android.runnertracker.core.data.room.RunEntity
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val runDao: RunDao,
): IMainRepository {

    override suspend fun insertRun(runEntity: RunEntity) = runDao.insertRun(runEntity)

    override suspend fun deleteRun(runEntity: RunEntity) = runDao.deleteRun(runEntity)

    override fun getTotalTimeInMillis() = runDao.getTotalTimeInMillis()

    override fun getTotalCaloriesBurned() = runDao.getTotalCaloriesBurned()

    override fun getTotalDistanceInMeters() = runDao.getTotalDistanceInMeters()

    override fun getTotalAverageSpeedInKmh() = runDao.getTotalAverageSpeedInKmh()

    override fun getAllRunsSortedByDate() = runDao.getAllRunsSortedByDate()

    override fun getAllRunsSortedByTimeInMillis() = runDao.getAllRunsSortedByTimeInMillis()

    override fun getAllRunsSortedByCaloriesBurned() = runDao.getAllRunsSortedByCaloriesBurned()

    override fun getAllRunsSortedByDistanceInMeters() = runDao.getAllRunsSortedByDistanceInMeters()

    override fun getAllRunsSortedByAverageSpeedInKmh() = runDao.getAllRunsSortedByAverageSpeedInKmh()
}