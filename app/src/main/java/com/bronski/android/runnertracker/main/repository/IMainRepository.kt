package com.bronski.android.runnertracker.main.repository

import androidx.lifecycle.LiveData
import com.bronski.android.runnertracker.core.data.room.RunEntity

interface IMainRepository {

    suspend fun deleteRun(runEntity: RunEntity)

    fun getTotalTimeInMillis(): LiveData<Long>

    fun getTotalCaloriesBurned(): LiveData<Int>

    fun getTotalDistanceInMeters(): LiveData<Int>

    fun getTotalAverageSpeedInKmh(): LiveData<Float>

    fun getAllRunsSortedByDate(): LiveData<List<RunEntity>>

    fun getAllRunsSortedByTimeInMillis(): LiveData<List<RunEntity>>

    fun getAllRunsSortedByCaloriesBurned(): LiveData<List<RunEntity>>

    fun getAllRunsSortedByDistanceInMeters(): LiveData<List<RunEntity>>

    fun getAllRunsSortedByAverageSpeedInKmh(): LiveData<List<RunEntity>>

}
