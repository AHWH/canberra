package com.sg.slightlyred.canberra.view.setup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.sg.slightlyred.canberra.R
import com.sg.slightlyred.canberra.databinding.FragmentSetupLanguageBinding
import com.sg.slightlyred.canberra.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [SetupLanguageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SetupLanguageFragment : Fragment(), AdapterView.OnItemClickListener, View.OnClickListener {
    private val TAG: String = "SetupLocationFragment"

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment SetupLanguageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = SetupLanguageFragment().apply {}
    }

    private var _viewBinding: FragmentSetupLanguageBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val viewModel: SetupLanguageViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _viewBinding = FragmentSetupLanguageBinding.inflate(layoutInflater, container, false)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.languagePreference.collect {
                    when (it) {
                        is ResponseState.Success -> { onLanguagePreferenceUpdate(it.data!!) }
                        else -> {}
                    }
                }
            }
        }

        // Setup language selector
        val languageListKey: Array<String> = resources.getStringArray(R.array.app_language_list_key)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.app_language_selector_item, languageListKey)
        viewBinding.setupLanguageSelector.setAdapter(arrayAdapter)
        viewBinding.setupLanguageSelector.onItemClickListener = this
        viewBinding.setupLanguageSelector.setText(languageListKey[0], false)

        viewBinding.setupLanguageNextButton.setOnClickListener(this)

        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.d(TAG, "User selected ${parent?.getItemAtPosition(position).toString()}")
        // List Preferences is expecting the value of the key to be stored
        val appPreferenceValue: String = resources.getStringArray(R.array.app_language_list_value)[position]
        if (appPreferenceValue != null) {
            viewModel.updateLanguagePreference(appPreferenceValue)
        }
    }

    fun onLanguagePreferenceUpdate(languageValue: String?) {
        // TODO: Implement app language support
    }

    override fun onClick(v: View?) {
        val action: NavDirections = SetupLanguageFragmentDirections.actionSetupLanguageFragmentToSetupLocationFragment()
        view?.findNavController()?.navigate(action)
    }
}