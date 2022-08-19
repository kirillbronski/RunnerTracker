package com.bronski.android.runnertracker.core.utils

import com.bronski.android.runnertracker.core.data.room.RunEntity

interface RecyclerItemListener {
    fun onItemClick(itemRun: RunEntity)
}