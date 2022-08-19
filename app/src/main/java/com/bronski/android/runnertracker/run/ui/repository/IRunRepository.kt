package com.bronski.android.runnertracker.run.ui.repository

import androidx.lifecycle.LiveData
import com.bronski.android.runnertracker.core.data.room.RunEntity

interface IRunRepository {
    fun getAllRunsSortedByDate(): LiveData<List<RunEntity>>
}