package com.bronski.android.runnertracker.setup.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bronski.android.runnertracker.R
import com.bronski.android.runnertracker.core.ui.BaseFragment
import com.bronski.android.runnertracker.databinding.FragmentSetupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetupFragment : BaseFragment<FragmentSetupBinding>() {

    private val viewModel by viewModels<SetupViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.continueTextView.setOnClickListener {
            findNavController().navigate(R.id.action_setupFragment_to_runFragment)
        }
    }

    override fun getViewBinding() = FragmentSetupBinding.inflate(layoutInflater)

}