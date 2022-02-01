package com.sg.slightlyred.canberra.view.setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sg.slightlyred.canberra.constants.AppPreferenceConstants
import com.sg.slightlyred.canberra.data.repository.AppPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetupLocationViewModel @Inject constructor(private val appPreferencesRepository: AppPreferencesRepository) : ViewModel() {
    private val TAG: String = SetupLanguageViewModel::class.java.name

    fun updateLocationAccessPreference(value: Boolean) {
        viewModelScope.launch {
            appPreferencesRepository.insertPreferenceBooleanValue(AppPreferenceConstants.KEY_LOCATION_ACCESS, value)
        }
    }

    fun updateLocationPrecisionPreference(value: Boolean) {
        viewModelScope.launch {
            appPreferencesRepository.insertPreferenceBooleanValue(AppPreferenceConstants.KEY_LOCATION_PRECISION, value)
        }
    }
}