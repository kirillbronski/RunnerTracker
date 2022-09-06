package com.bronski.android.runnertracker.tracking.repository

import com.bronski.android.runnertracker.core.data.room.RunDao
import com.bronski.android.runnertracker.core.data.room.RunEntity
import javax.inject.Inject

class TrackingRepositoryImpl @Inject constructor(
    private val runDao: RunDao,
) : ITrackingRepository {

    override suspend fun insertRun(runEntity: RunEntity) = runDao.insertRun(runEntity)
}