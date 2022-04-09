package com.sg.slightlyred.canberra.view.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.sg.slightlyred.canberra.adapter.BusStopsRecyclerViewAdapter
import com.sg.slightlyred.canberra.data.model.bus.BusStop
import com.sg.slightlyred.canberra.databinding.FragmentCurrentLocationBinding
import com.sg.slightlyred.canberra.utils.ResponseState
import com.sg.slightlyred.canberra.view.main.MainViewModel
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [CurrentLocationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CurrentLocationFragment : Fragment() {
    private val LOG_TAG = CurrentLocationFragment::class.java.name

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment CurrentLocationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = CurrentLocationFragment().apply { arguments = Bundle().apply {} }
    }

    // ViewBinding and ViewModel
    private var _viewBinding: FragmentCurrentLocationBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val parentViewModel: MainViewModel by activityViewModels()

    // Views
    private var stopsRecyclerView: RecyclerView? = null
    private var busStopsRecyclerViewAdapter: BusStopsRecyclerViewAdapter? = null

    // Others
    private val recyclerViewNearbyBusStops: MutableList<BusStop> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _viewBinding = FragmentCurrentLocationBinding.inflate(inflater, container, false)
        stopsRecyclerView = viewBinding.currentLocationRecyclerView
        busStopsRecyclerViewAdapter = BusStopsRecyclerViewAdapter(recyclerViewNearbyBusStops)
        stopsRecyclerView?.adapter = busStopsRecyclerViewAdapter
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                parentViewModel.nearbyBusStops.collect {
                    when (it) {
                        is ResponseState.Success -> { showNearbyBusStops(it.data!!) }
                        else -> {}
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        busStopsRecyclerViewAdapter = null
        stopsRecyclerView = null
        _viewBinding = null
    }


    private fun showNearbyBusStops(nearbyBusStops: List<BusStop>) {
        recyclerViewNearbyBusStops.clear()
        recyclerViewNearbyBusStops.addAll(nearbyBusStops)
        busStopsRecyclerViewAdapter?.notifyDataSetChanged()
    }
}