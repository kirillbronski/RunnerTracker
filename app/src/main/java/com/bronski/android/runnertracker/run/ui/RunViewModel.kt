package com.bronski.android.runnertracker.run.ui

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.bronski.android.runnertracker.core.data.room.RunEntity
import com.bronski.android.runnertracker.core.utils.SortType
import com.bronski.android.runnertracker.run.ui.repository.IRunRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RunViewModel @Inject constructor(
    private val runRepositoryImpl: IRunRepository
) : ViewModel() {

    private val runsSortedByDate =
        runRepositoryImpl.getAllRunsSortedByDate()
    private val runsSortedByDistanceInMeters =
        runRepositoryImpl.getAllRunsSortedByDistanceInMeters()
    private val runsSortedByCaloriesBurned =
        runRepositoryImpl.getAllRunsSortedByCaloriesBurned()
    private val runsSortedByTimeInMillis =
        runRepositoryImpl.getAllRunsSortedByTimeInMillis()
    private val runsSortedByAverageSpeedInKmh =
        runRepositoryImpl.getAllRunsSortedByAverageSpeedInKmh()

    val runs = MediatorLiveData<List<RunEntity>>()

    var sortType = SortType.DATE

    init {
        runs.addSource(runsSortedByDate) { result ->
            if (sortType == SortType.DATE) {
                result?.let {
                    runs.value = it
                }
            }
        }
        runs.addSource(runsSortedByAverageSpeedInKmh) { result ->
            if (sortType == SortType.AVG_SPEED) {
                result?.let {
                    runs.value = it
                }
            }
        }
        runs.addSource(runsSortedByCaloriesBurned) { result ->
            if (sortType == SortType.CALORIES_BURNED) {
                result?.let {
                    runs.value = it
                }
            }
        }
        runs.addSource(runsSortedByTimeInMillis) { result ->
            if (sortType == SortType.RUNNING_TIME) {
                result?.let {
                    runs.value = it
                }
            }
        }
        runs.addSource(runsSortedByDistanceInMeters) { result ->
            if (sortType == SortType.DISTANCE) {
                result?.let {
                    runs.value = it
                }
            }
        }
    }

    fun sortRuns(sortType: SortType) = when (sortType) {
        SortType.DATE -> runsSortedByDate.value?.let { runs.value = it }
        SortType.RUNNING_TIME -> runsSortedByTimeInMillis.value?.let { runs.value = it }
        SortType.DISTANCE -> runsSortedByDistanceInMeters.value?.let { runs.value = it }
        SortType.CALORIES_BURNED -> runsSortedByCaloriesBurned.value?.let { runs.value = it }
        SortType.AVG_SPEED -> runsSortedByAverageSpeedInKmh.value?.let { runs.value = it }
    }.also {
        this.sortType = sortType
    }
}