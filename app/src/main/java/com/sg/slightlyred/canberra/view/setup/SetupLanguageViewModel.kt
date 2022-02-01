package com.sg.slightlyred.canberra.view.setup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.sg.slightlyred.canberra.constants.AppPreferenceConstants
import com.sg.slightlyred.canberra.data.repository.AppPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetupLanguageViewModel @Inject constructor(private val appPreferencesRepository: AppPreferencesRepository) : ViewModel() {
    private val TAG: String = SetupLanguageViewModel::class.java.name

    private var _languagePreference: LiveData<String?> = liveData {
        try {
            emit(appPreferencesRepository.getPreferenceStringValue(AppPreferenceConstants.KEY_APP_LANGUAGE, null))
        } catch (ex: ClassCastException) {
            Log.e(TAG, "Error retrieving language preference")
            emit(null)
        }
    }
    val languagePreference: LiveData<String?> get() = _languagePreference

    fun updateLanguagePreference(value: String) {
        viewModelScope.launch { appPreferencesRepository.insertPreferenceStringValue(AppPreferenceConstants.KEY_APP_LANGUAGE,value) }
    }
}