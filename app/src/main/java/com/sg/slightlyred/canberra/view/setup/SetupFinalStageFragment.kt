package com.sg.slightlyred.canberra.view.setup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.sg.slightlyred.canberra.databinding.FragmentSetupFinalStageBinding
import com.sg.slightlyred.canberra.utils.ResponseState
import com.sg.slightlyred.canberra.view.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [SetupFinalStageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SetupFinalStageFragment : Fragment() {
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment SetupFinalStageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = SetupFinalStageFragment().apply {}
    }
    private val TAG: String = "SetupFinalStageFragment"

    private var _viewBinding: FragmentSetupFinalStageBinding? = null
    private val viewBinding: FragmentSetupFinalStageBinding get() = _viewBinding!!
    private val viewModel: SetupFinalStageViewModel by viewModels()
    private val parentViewModel: SetupViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _viewBinding = FragmentSetupFinalStageBinding.inflate(layoutInflater, container, false)
        viewBinding.setupFinalStageBackButton.setOnClickListener{ onBackButtonClick(it) }
        viewBinding.setupFinalStageStartButton.setOnClickListener{ onStartButtonClick(it) }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                parentViewModel.getBusStopsAndSaveStatus.collect { onAllBusStopResponse(it) }
            }
        }
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    // ======
    private fun onBackButtonClick(view: View) {
        view.findNavController().navigateUp()
    }

    private fun onStartButtonClick(view: View) {
        Log.d(TAG, "Updates first run to False")
        viewModel.updateFirstRun()
        val intent: Intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }

    private fun onAllBusStopResponse(responseState: ResponseState<Boolean>) {
        when (responseState) {
            is ResponseState.Success -> {
                Log.d(TAG, "Saving of bus stops completes, allowing user to proceed")
                viewBinding.setupFinalStageProgressBar.hide()
                viewBinding.setupFinalStageStartButton.visibility = View.VISIBLE
            }
            is ResponseState.Error -> {}
            else -> {}
        }
    }
}