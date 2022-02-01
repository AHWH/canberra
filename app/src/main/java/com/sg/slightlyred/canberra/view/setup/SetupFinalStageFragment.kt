package com.sg.slightlyred.canberra.view.setup

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.sg.slightlyred.canberra.data.model.bus.BusStop
import com.sg.slightlyred.canberra.databinding.FragmentSetupFinalStageBinding
import com.sg.slightlyred.canberra.view.main.MainActivity
import com.sg.slightlyred.canberra.utils.ResponseState
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [SetupFinalStageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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

    private var _viewBinding: FragmentSetupFinalStageBinding? = null
    private val viewBinding: FragmentSetupFinalStageBinding get() = _viewBinding!!
    private val parentViewModel: SetupViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _viewBinding = FragmentSetupFinalStageBinding.inflate(layoutInflater, container, false)
        viewBinding.setupFinalStageBackButton.setOnClickListener{ onBackButtonClick(it) }
        viewBinding.setupFinalStageStartButton.setOnClickListener{ onStartButtonClick(it) }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                parentViewModel.allBusStop.collect { onAllBusStopResponse(it) }
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
        val intent: Intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }

    private fun onAllBusStopResponse(responseState: ResponseState<List<BusStop>>) {
        when (responseState) {
            is ResponseState.Success -> {
                viewBinding.setupFinalStageProgressBar.hide()
                viewBinding.setupFinalStageStartButton.visibility = View.VISIBLE
            }
            is ResponseState.Error -> {}
            else -> {}
        }
    }

    private fun onProgress() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            viewBinding.setupFinalStageProgressBar.setProgressCompat(60, true)
        }, 2000)

        val handler2 = Handler(Looper.getMainLooper())
        handler2.postDelayed({
            viewBinding.setupFinalStageProgressBar.hide()
            viewBinding.setupFinalStageStartButton.visibility = View.VISIBLE
        }, 5000)
    }
}