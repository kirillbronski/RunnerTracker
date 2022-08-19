package com.bronski.android.runnertracker.main.ui

import androidx.lifecycle.ViewModel
import com.bronski.android.runnertracker.tracking.repository.TrackingRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: TrackingRepositoryImpl,
) : ViewModel() {
}