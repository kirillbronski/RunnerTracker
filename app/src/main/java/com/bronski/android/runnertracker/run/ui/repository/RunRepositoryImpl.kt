package com.bronski.android.runnertracker.run.ui.repository

import com.bronski.android.runnertracker.core.data.room.RunDao
import com.bronski.android.runnertracker.core.utils.BaseResult
import javax.inject.Inject

class RunRepositoryImpl @Inject constructor(
    private val runDao: RunDao,
) : IRunRepository {

    override suspend fun getAllRunsSortedByDate(): BaseResult =
        runCatching {
            runDao.getAllRunsSortedByDate()
        }.fold(
            onSuccess = {
                BaseResult.Success(it)
            },
            onFailure = {
                BaseResult.Error(it.message)
            }
        )
}
