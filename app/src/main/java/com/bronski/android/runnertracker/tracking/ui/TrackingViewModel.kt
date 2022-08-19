package com.bronski.android.runnertracker.tracking.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bronski.android.runnertracker.core.data.room.RunEntity
import com.bronski.android.runnertracker.main.repository.ITrackingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackingViewModel @Inject constructor(
    private val trackingRepository: ITrackingRepository,
) : ViewModel() {

    fun insertRun(runEntity: RunEntity) = viewModelScope.launch {
        trackingRepository.insertRun(runEntity)
    }
}