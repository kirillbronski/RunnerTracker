package com.bronski.android.runnertracker.main.repository

import com.bronski.android.runnertracker.core.data.room.RunEntity

interface IMainRepository {

    suspend fun deleteRun(runEntity: RunEntity)


}
