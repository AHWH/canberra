package com.sg.slightlyred.canberra.view.setup

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.sg.slightlyred.canberra.constants.AppConstants
import com.sg.slightlyred.canberra.databinding.FragmentSetupLocationBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [SetupLocationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SetupLocationFragment : Fragment(), ActivityResultCallback<Map<String, Boolean>> {
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment SetupLocationFragment.
         */
        @JvmStatic
        fun newInstance() = SetupLocationFragment().apply {}
    }
    private val LOG_TAG = SetupLocationFragment::class.java.name

    private var _viewBinding: FragmentSetupLocationBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val viewModel: SetupLocationViewModel by viewModels()

    private lateinit var locationPermissionRequest: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _viewBinding = FragmentSetupLocationBinding.inflate(inflater, container, false)
        viewBinding.setupLocationBackButton.setOnClickListener { onBackButtonClick(it) }
        viewBinding.setupLocationNextButton.setOnClickListener { onNextButtonClick(it) }

        viewBinding.setupLocationGrantAccessButton.setOnClickListener {
            onClickToRequestLocationPermission(it)
        }

        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    override fun onActivityResult(permissionResults: Map<String, Boolean>?) {
        if (permissionResults != null) {
            when {
                permissionResults.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Writes to location preference, location: allowed, precision: high
                    Log.i(LOG_TAG, "User granted FINE_LOCATION permission")
                    viewModel.updateLocationAccessPreference(true)
                    viewModel.updateLocationPrecisionPreference(true)
                }
                permissionResults.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Writes to location preference, location: allowed, precision: low
                    Log.i(LOG_TAG, "User granted COARSE_LOCATION permission")
                    viewModel.updateLocationAccessPreference(true)
                    viewModel.updateLocationPrecisionPreference(false)
                } else -> {
                    // Writes to location preference, location: denied
                    Log.i(LOG_TAG, "User denied location permission")
                    viewModel.updateLocationAccessPreference(false)
                    viewModel.updateLocationPrecisionPreference(false)
                }
            }
            onNextButtonClick(viewBinding.root)
        }
    }

    private fun onClickToRequestLocationPermission(view: View) {
        when {
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                // ACCESS_COARSE_LOCATION is always granted as long permission for location is given
                onNextButtonClick(view)
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                Log.e(LOG_TAG, "Impossible situation happened: User denied permission and we are still in this page")
            }
            else -> {
                locationPermissionRequest.launch(AppConstants.LOCATION_PERMISSIONS)
            }
        }
    }

    private fun onBackButtonClick(view: View) {
        view.findNavController().navigateUp()
    }

    private fun onNextButtonClick(view: View) {
        val action: NavDirections = SetupLocationFragmentDirections.actionSetupLocationFragmentToSetupFinalStageFragment()
        view.findNavController().navigate(action)
    }
}