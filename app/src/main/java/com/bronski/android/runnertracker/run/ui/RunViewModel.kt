package com.bronski.android.runnertracker.run.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bronski.android.runnertracker.core.data.room.RunEntity
import com.bronski.android.runnertracker.core.utils.BaseResult
import com.bronski.android.runnertracker.run.ui.repository.IRunRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RunViewModel @Inject constructor(
    private val runRepositoryImpl: IRunRepository
) : ViewModel() {

    private var _runsSortedByDate = MutableLiveData<List<RunEntity>>()
    val runsSortedByDate: MutableLiveData<List<RunEntity>> = _runsSortedByDate

    init {
        getAllRunsSortedByDate()
    }

    fun getAllRunsSortedByDate() {
        viewModelScope.launch(Dispatchers.IO) {
            runRepositoryImpl.getAllRunsSortedByDate().run {
                when (this) {
                    is BaseResult.Success -> {
                        _runsSortedByDate.postValue(this.runList)
                    }
                    is BaseResult.Error -> {
                        Timber.d(this.errorMessage)
                    }
                }
            }
        }
    }

}