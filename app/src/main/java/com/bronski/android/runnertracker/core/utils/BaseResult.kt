package com.bronski.android.runnertracker.core.utils

import com.bronski.android.runnertracker.core.data.room.RunEntity

sealed class BaseResult {
    class Success(val runList: List<RunEntity>) : BaseResult()
    class Error(val errorMessage: String?) : BaseResult()
}
