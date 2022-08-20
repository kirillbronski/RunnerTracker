package com.bronski.android.runnertracker.run.ui.repository

import com.bronski.android.runnertracker.core.utils.BaseResult

interface IRunRepository {
    suspend fun getAllRunsSortedByDate(): BaseResult
}