package com.bronski.android.runnertracker.statistics.ui

import androidx.lifecycle.ViewModel
import com.bronski.android.runnertracker.main.repository.MainRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val mainRepository: MainRepositoryImpl,
) : ViewModel() {
}