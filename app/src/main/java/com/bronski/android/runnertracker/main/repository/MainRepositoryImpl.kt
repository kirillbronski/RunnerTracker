package com.bronski.android.runnertracker.main.repository

import com.bronski.android.runnertracker.core.data.room.RunDao
import com.bronski.android.runnertracker.core.data.room.RunEntity
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val runDao: RunDao,
) : IMainRepository {

    override suspend fun deleteRun(runEntity: RunEntity) = runDao.deleteRun(runEntity)

}