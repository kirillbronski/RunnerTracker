package com.bronski.android.runnertracker.setup.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bronski.android.runnertracker.R
import com.bronski.android.runnertracker.core.ui.BaseFragment
import com.bronski.android.runnertracker.core.utils.Constants.KEY_FIRST_TIME_TOGGLE
import com.bronski.android.runnertracker.core.utils.Constants.KEY_NAME
import com.bronski.android.runnertracker.core.utils.Constants.KEY_WEIGHT
import com.bronski.android.runnertracker.databinding.FragmentSetupBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SetupFragment : BaseFragment<FragmentSetupBinding>() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @set:Inject
    var isFirstAppOpen = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isFirstAppOpen) {
            val navOption = NavOptions.Builder()
                .setPopUpTo(R.id.setupFragment, true)
                .build()
            findNavController().navigate(
                R.id.action_setupFragment_to_runFragment,
                savedInstanceState,
                navOption
            )
        }
        binding.continueTextView.setOnClickListener {
            if (writePersonalDataToSharedPreferences()) {
                navigateToRunFragment()
            } else {
                Snackbar.make(requireView(), "Please enter all the fields", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun writePersonalDataToSharedPreferences(): Boolean {
        val name = binding.nameEditText.text.toString()
        val weight = binding.weightEditText.text.toString()
        if (name.isEmpty() || weight.isEmpty()) {
            return false
        }
        sharedPreferences.edit()
            .putString(KEY_NAME, name)
            .putFloat(KEY_WEIGHT, weight.toFloat())
            .putBoolean(KEY_FIRST_TIME_TOGGLE, false)
            .apply()
        return true
    }

    private fun navigateToRunFragment() {
        findNavController().navigate(R.id.action_setupFragment_to_runFragment)
    }

    override fun getViewBinding() = FragmentSetupBinding.inflate(layoutInflater)

}