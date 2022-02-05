package com.sg.slightlyred.canberra.view.main

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.grum.geocalc.BoundingArea
import com.grum.geocalc.Coordinate
import com.grum.geocalc.EarthCalc
import com.grum.geocalc.Point
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
                viewModel.fetchAllBusStops()
            }
        }
    }

    private fun collateNearestBusStops(currentLocation: Location?) {
        Log.i("TEST", "location updated")
        val nearestBusStops: MutableList<BusStop> = mutableListOf();
        if (currentLocation != null && (allBusStops != null && allBusStops!!.isNotEmpty())) {
            Log.i("TEST", "??")
            val boundingArea: BoundingArea = getBoundingArea(currentLocation)
            allBusStops!!.forEach {
                if (withinAcceptableDistance(boundingArea, it)) {
                    nearestBusStops += it
                }
            }
            nearestBusStops.forEach {
                Log.i("TEST", it.toString())
            }
        }
    }

    private fun withinAcceptableDistance(boundingArea: BoundingArea, busStop: BusStop): Boolean {
        val busStopPoint: Point = Point.at(Coordinate.fromDegrees(busStop.latitude), Coordinate.fromDegrees(busStop.longitude))
        return boundingArea.contains(busStopPoint)
    }

    private fun getBoundingArea(currentLocation: Location): BoundingArea {
        val currentPoint: Point = Point.at(Coordinate.fromDegrees(currentLocation.latitude), Coordinate.fromDegrees(currentLocation.longitude))
        return EarthCalc.gcd.around(currentPoint, 500.00)
    }
}