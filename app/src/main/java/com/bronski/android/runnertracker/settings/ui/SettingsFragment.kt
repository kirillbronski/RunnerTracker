package com.bronski.android.runnertracker.settings.ui

import androidx.fragment.app.viewModels
import com.bronski.android.runnertracker.core.ui.BaseFragment
import com.bronski.android.runnertracker.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    private val viewModel by viewModels<SettingsViewModel>()

    override fun getViewBinding() = FragmentSettingsBinding.inflate(layoutInflater)

}