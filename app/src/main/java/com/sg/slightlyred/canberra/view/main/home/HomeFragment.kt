package com.sg.slightlyred.canberra.view.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.sg.slightlyred.canberra.adapter.HomeTabViewPagerAdapter
import com.sg.slightlyred.canberra.data.model.bus.BusStop
import com.sg.slightlyred.canberra.databinding.FragmentHomeBinding
import com.sg.slightlyred.canberra.utils.ResponseState
import com.sg.slightlyred.canberra.view.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val LOG_TAG = HomeFragment::class.java.name
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = HomeFragment().apply { arguments = Bundle().apply {} }
    }

    // ViewBinding and ViewModel
    private var _viewBinding: FragmentHomeBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val viewModel: HomeViewModel by viewModels()
    private val parentViewModel: MainViewModel by activityViewModels()

    private var _viewPagerAdapter: HomeTabViewPagerAdapter? = null
    private val viewPagerAdapter get() = _viewPagerAdapter!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewPagerAdapter = HomeTabViewPagerAdapter(childFragmentManager, lifecycle)
        viewBinding.homeBottomSheet.homeTabPager.adapter = viewPagerAdapter
        TabLayoutMediator(viewBinding.homeBottomSheet.homeTabLayout, viewBinding.homeBottomSheet.homeTabPager) { tab, postion -> {
            //TODO: Do something
        }}.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}