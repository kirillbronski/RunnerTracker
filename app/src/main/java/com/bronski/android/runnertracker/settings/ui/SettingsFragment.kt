package com.bronski.android.runnertracker.settings.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import com.bronski.android.runnertracker.R
import com.bronski.android.runnertracker.core.ui.BaseFragment
import com.bronski.android.runnertracker.core.utils.Constants.KEY_NAME
import com.bronski.android.runnertracker.core.utils.Constants.KEY_WEIGHT
import com.bronski.android.runnertracker.databinding.FragmentSettingsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val viewModel by viewModels<SettingsViewModel>()

    override fun getViewBinding() = FragmentSettingsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadsFieldsFromSharedPref()
        binding.applyButton.setOnClickListener {
            if (applyChangesToSharedPref()) {
                Snackbar.make(view, "Saved Changes", Snackbar.LENGTH_LONG).show()
            } else
                Snackbar.make(view, "Please fill out all the fields", Snackbar.LENGTH_LONG).show()
        }
    }


    private fun loadsFieldsFromSharedPref() {
        val name = sharedPreferences.getString(KEY_NAME, "")
        val weight = sharedPreferences.getFloat(KEY_WEIGHT, 80f)
        binding.nameEt.setText(name)
        binding.weightEt.setText(weight.toString())
    }

    private fun applyChangesToSharedPref(): Boolean {
        val name = binding.nameEt.text.toString()
        val weight = binding.weightEt.text.toString()
        if (name.isEmpty() || weight.isEmpty()) {
            return false
        }
        sharedPreferences.edit()
            .putString(KEY_NAME, name)
            .putFloat(KEY_WEIGHT, weight.toFloat())
            .apply()
//        val toolbarText = "Let's go $name"
//        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
//        toolbar.title = toolbarText
        return true
    }

}