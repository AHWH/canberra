package com.sg.slightlyred.canberra.view.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.observable.eventdata.CameraChangedEventData
import com.mapbox.maps.extension.observable.eventdata.MapLoadingErrorEventData
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.delegates.listeners.OnCameraChangeListener
import com.mapbox.maps.plugin.delegates.listeners.OnMapLoadErrorListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.sg.slightlyred.canberra.R
import com.sg.slightlyred.canberra.constants.AppConstants
import com.sg.slightlyred.canberra.data.model.LocationResponse
import com.sg.slightlyred.canberra.databinding.FragmentMapboxBinding
import com.sg.slightlyred.canberra.view.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [MapboxFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MapboxFragment : Fragment(), ActivityResultCallback<Map<String, Boolean>>, OnCameraChangeListener, OnMapLoadErrorListener {
    private val LOG_TAG = MapboxFragment::class.java.name
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment MapboxFragment.
         */
        @JvmStatic
        fun newInstance() =
            MapboxFragment().apply {
                arguments = Bundle().apply {}
            }
    }
    private var _viewBinding: FragmentMapboxBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val viewModel: MapboxFragmentViewModel by viewModels()
    private val parentViewModel: MainViewModel by activityViewModels()

    private lateinit var cameraOptionsBuilder: CameraOptions.Builder
    private lateinit var locationPermissionRequest: ActivityResultLauncher<Array<String>>
    //View Variables (NOT CALL THEM and NULL THEM when view is destroyed)
    private var _mapboxMap: MapboxMap? = null
    private val mapboxMap get() = _mapboxMap!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), this)
        cameraOptionsBuilder = CameraOptions.Builder()
        cameraOptionsBuilder.zoom(AppConstants.DEFAULT_ZOOM_VALUE)
        cameraOptionsBuilder.bearing(0.0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _viewBinding = FragmentMapboxBinding.inflate(layoutInflater)
        viewModel.getLocation().observe(viewLifecycleOwner) { onLocationDataChange(it) }

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _mapboxMap = viewBinding.mapboxMapView.getMapboxMap()

        mapboxMap.loadStyleUri(Style.DARK) {
            viewBinding.mapboxMapView.location.updateSettings {
                enabled = true
                pulsingEnabled = true
            }
        }

        mapboxMap.addOnCameraChangeListener(this)
        if (isLocationAccessGranted()) {
            // Only triangulate user's location when given permission, else fallback to user zoomed-in location
            viewModel.requestLocation()
        }

        viewBinding.mapboxFixLocationButton.setOnClickListener { onFixLocationButtonClick(it) }
    }

    override fun onDestroyView() {
        mapboxMap.removeOnCameraChangeListener(this)
        super.onDestroyView()
        _viewBinding = null
        _mapboxMap = null
    }


    // Location Permissions related
    override fun onActivityResult(permissionResults: Map<String, Boolean>?) {
        if (permissionResults != null) {
            when {
                permissionResults.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) ||
                        permissionResults.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    viewModel.requestLocation()
                }
                else -> {
                    Log.i(LOG_TAG, "User denied location permissions")
                }
            }
        }
    }

    private fun isLocationAccessGranted(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun isFineLocationAccessGranted(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun launchLocationPermissions(permission: String) {
        when {
            shouldShowRequestPermissionRationale(permission) -> {
                when (permission) {
                    Manifest.permission.ACCESS_COARSE_LOCATION -> {
                        showPermissionRationale(R.string.permission_location_access_title, R.string.permission_location_access_message)
                    }
                    Manifest.permission.ACCESS_FINE_LOCATION -> {
                        showPermissionRationale(R.string.permission_fine_location_title, R.string.permission_fine_location_message)
                    }
                }
            }
            else -> {
                locationPermissionRequest.launch(AppConstants.LOCATION_PERMISSIONS)
            }
        }
    }

    private fun showPermissionRationale(title: Int, message: Int) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(title))
            .setMessage(resources.getString(message))
            .setIcon(ResourcesCompat.getDrawable(resources, R.drawable.ic_location_on_black_24dp, null))
            .setPositiveButton(resources.getString(android.R.string.ok)) { dialog, which -> locationPermissionRequest.launch(AppConstants.LOCATION_PERMISSIONS)}
            .setNegativeButton(resources.getString(android.R.string.cancel)) { dialog, which -> Log.d(LOG_TAG, "User rejects permission request despite rationale")}
            .show()
    }

    private fun onLocationDataChange(locationResponse: LocationResponse) {
        if (locationResponse.isError()) {
            // TODO: Error handling
            Log.e(LOG_TAG, locationResponse.errorMessage)
        } else {
            setCameraToLocation(locationResponse.location!!)
        }
    }


    // View-Related On-click Listeners
    private fun onFixLocationButtonClick(view: View) {
        if (isLocationAccessGranted()) {
            if (isFineLocationAccessGranted()) {
                viewModel.requestLocation()
            } else {
                launchLocationPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        } else {
            launchLocationPermissions(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    // MapBox related
    override fun onMapLoadError(eventData: MapLoadingErrorEventData) {
        //Log.e("HomeFragment", eventData.message)
    }

    override fun onCameraChanged(eventData: CameraChangedEventData) {
        val currentPosition: Point = mapboxMap.cameraState.center
        val location: Location = Location(AppConstants.APP_MAP_CAMERA_CENTER)
        location.latitude = currentPosition.latitude()
        location.longitude = currentPosition.longitude()
        location.altitude = currentPosition.altitude()
        parentViewModel.updateLocation(location)
    }

    private fun setCameraToLocation(location: Location) {
        cameraOptionsBuilder.center(Point.fromLngLat(location.longitude, location.latitude))
        val cameraOptions:CameraOptions = cameraOptionsBuilder.build()
        mapboxMap.flyTo(cameraOptions)
    }
}