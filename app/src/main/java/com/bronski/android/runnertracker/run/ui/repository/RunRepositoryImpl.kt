package com.bronski.android.runnertracker.run.ui.repository

import androidx.lifecycle.LiveData
import com.bronski.android.runnertracker.core.data.room.RunDao
import com.bronski.android.runnertracker.core.data.room.RunEntity
import javax.inject.Inject

class RunRepositoryImpl @Inject constructor(
    private val runDao: RunDao
) : IRunRepository {

    override fun getAllRunsSortedByDate() = runDao.getAllRunsSortedByDate()
}