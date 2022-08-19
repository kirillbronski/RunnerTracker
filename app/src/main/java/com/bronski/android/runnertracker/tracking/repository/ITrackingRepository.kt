package com.bronski.android.runnertracker.main.repository

import androidx.lifecycle.LiveData
import com.bronski.android.runnertracker.core.data.room.RunEntity

interface ITrackingRepository {

    suspend fun insertRun(runEntity: RunEntity)

}