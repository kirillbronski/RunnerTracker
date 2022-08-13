package com.bronski.android.runnertracker.statistics.ui

import androidx.fragment.app.viewModels
import com.bronski.android.runnertracker.core.ui.BaseFragment
import com.bronski.android.runnertracker.databinding.FragmentStatisticsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>() {

    private val viewModel by viewModels<StatisticsViewModel>()

    override fun getViewBinding() = FragmentStatisticsBinding.inflate(layoutInflater)

}