package com.bronski.android.runnertracker.tracking.repository

import com.bronski.android.runnertracker.core.data.room.RunEntity

interface ITrackingRepository {

    suspend fun insertRun(runEntity: RunEntity)

}
