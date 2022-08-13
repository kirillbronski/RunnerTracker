package com.bronski.android.runnertracker.setup.ui

import androidx.fragment.app.viewModels
import com.bronski.android.runnertracker.core.ui.BaseFragment
import com.bronski.android.runnertracker.databinding.FragmentSetupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetupFragment : BaseFragment<FragmentSetupBinding>() {

    private val viewModel by viewModels<SetupViewModel>()

    override fun getViewBinding() = FragmentSetupBinding.inflate(layoutInflater)

}