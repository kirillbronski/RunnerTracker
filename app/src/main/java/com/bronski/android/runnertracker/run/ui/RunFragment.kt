package com.bronski.android.runnertracker.run.ui

import androidx.fragment.app.viewModels
import com.bronski.android.runnertracker.core.ui.BaseFragment
import com.bronski.android.runnertracker.databinding.FragmentRunBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RunFragment : BaseFragment<FragmentRunBinding>() {

    private val viewModel by viewModels<RunViewModel>()

    override fun getViewBinding() = FragmentRunBinding.inflate(layoutInflater)

}