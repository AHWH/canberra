package com.sg.slightlyred.canberra.view.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sg.slightlyred.canberra.R
import com.sg.slightlyred.canberra.databinding.FragmentFavouritesBinding


/**
 * A simple [Fragment] subclass.
 * Use the [FavouritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavouritesFragment : Fragment() {
    private val LOG_TAG = FavouritesFragment::class.java.name

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment FavouritesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = FavouritesFragment().apply { arguments = Bundle().apply {} }
    }

    // Viewbindings and ViewModels
    private var _viewBinding: FragmentFavouritesBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _viewBinding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}