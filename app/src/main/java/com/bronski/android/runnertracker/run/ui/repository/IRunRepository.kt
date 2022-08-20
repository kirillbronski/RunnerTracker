package com.bronski.android.runnertracker.run.ui.repository

import androidx.lifecycle.LiveData
import com.bronski.android.runnertracker.core.data.room.RunEntity

interface IRunRepository {

    fun getAllRunsSortedByDate(): LiveData<List<RunEntity>>

    fun getAllRunsSortedByTimeInMillis(): LiveData<List<RunEntity>>

    fun getAllRunsSortedByCaloriesBurned(): LiveData<List<RunEntity>>

    fun getAllRunsSortedByDistanceInMeters(): LiveData<List<RunEntity>>

    fun getAllRunsSortedByAverageSpeedInKmh(): LiveData<List<RunEntity>>
}