package com.bronski.android.runnertracker.run.ui

import androidx.lifecycle.ViewModel
import com.bronski.android.runnertracker.run.ui.repository.IRunRepository
import com.bronski.android.runnertracker.run.ui.repository.RunRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RunViewModel @Inject constructor(
    private val runRepositoryImpl: IRunRepository
) : ViewModel() {

    val runsSortedByDate = runRepositoryImpl.getAllRunsSortedByDate()

}