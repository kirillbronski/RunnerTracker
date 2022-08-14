package com.bronski.android.runnertracker.tracking.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bronski.android.runnertracker.core.ui.BaseFragment
import com.bronski.android.runnertracker.databinding.FragmentTrackingBinding
import com.google.android.gms.maps.GoogleMap
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackingFragment : BaseFragment<FragmentTrackingBinding>() {

    private val viewModel by viewModels<TrackingViewModel>()

    private var googleMap: GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync {
            googleMap = it
        }
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun getViewBinding() = FragmentTrackingBinding.inflate(layoutInflater)

}