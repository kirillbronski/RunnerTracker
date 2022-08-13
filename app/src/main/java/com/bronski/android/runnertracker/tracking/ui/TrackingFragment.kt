package com.bronski.android.runnertracker.tracking.ui

import androidx.fragment.app.viewModels
import com.bronski.android.runnertracker.core.ui.BaseFragment
import com.bronski.android.runnertracker.databinding.FragmentTrackingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackingFragment : BaseFragment<FragmentTrackingBinding>() {

    private val viewModel by viewModels<TrackingViewModel>()

    override fun getViewBinding() = FragmentTrackingBinding.inflate(layoutInflater)

}