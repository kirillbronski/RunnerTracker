package com.bronski.android.runnertracker.statistics.repository

import androidx.lifecycle.LiveData
import com.bronski.android.runnertracker.core.data.room.RunEntity

interface IStatisticsRepository {

    fun getTotalTimeInMillis(): LiveData<Long>

    fun getTotalCaloriesBurned(): LiveData<Int>

    fun getTotalDistanceInMeters(): LiveData<Int>

    fun getTotalAverageSpeedInKmh(): LiveData<Float>

    fun getAllRunsSortedByDate(): LiveData<List<RunEntity>>

}
