package com.sg.slightlyred.canberra.view.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sg.slightlyred.canberra.databinding.FragmentCurrentLocationBinding

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _viewBinding = FragmentCurrentLocationBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}