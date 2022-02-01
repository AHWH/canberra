package com.sg.slightlyred.canberra.view.main

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sg.slightlyred.canberra.data.model.bus.BusStop
import com.sg.slightlyred.canberra.databinding.ActivityMainBinding
import com.sg.slightlyred.canberra.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private var allBusStops: List<BusStop>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.allBusStop.collect {
                        when (it) {
                            is ResponseState.Loading -> {}
                            is ResponseState.Success -> { allBusStops = it.data }
                            is ResponseState.Error -> {}
                        }
                    }
                }
                launch {
                    viewModel.currentLocation.collect {
                        when (it) {
                            is ResponseState.Loading -> {}
                            is ResponseState.Success -> { collateNearestBusStops(it.data) }
                            is ResponseState.Error -> {}
                        }
                    }
                }
            }
        }
    }

    private fun collateNearestBusStops(currentLocation: Location?) {
        val nearestBusStops: MutableList<BusStop> = mutableListOf();
        if (currentLocation != null && (allBusStops != null && allBusStops!!.isNotEmpty())) {
            allBusStops!!.forEach {
                if (withinAcceptableDistance(currentLocation, it)) {
                    nearestBusStops += it
                }
            }
        }
    }

    private fun withinAcceptableDistance(currentLocation: Location, busStop: BusStop): Boolean {

        return false
    }
}