package com.bronski.android.runnertracker.main.repository

import androidx.lifecycle.LiveData
import com.bronski.android.runnertracker.core.data.room.RunEntity

interface IMainRepository {

    suspend fun deleteRun(runEntity: RunEntity)

    fun getTotalTimeInMillis(): LiveData<Long>

    fun getTotalCaloriesBurned(): LiveData<Int>

    fun getTotalDistanceInMeters(): LiveData<Int>

    fun getTotalAverageSpeedInKmh(): LiveData<Float>

}
