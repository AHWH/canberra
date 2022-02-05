package com.sg.slightlyred.canberra.view.setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sg.slightlyred.canberra.constants.AppPreferenceConstants
import com.sg.slightlyred.canberra.data.repository.AppPreferencesRepository
import com.sg.slightlyred.canberra.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetupLanguageViewModel @Inject constructor(private val appPreferencesRepository: AppPreferencesRepository) : ViewModel() {
    private var _languagePreference: MutableStateFlow<ResponseState<String>> = MutableStateFlow(ResponseState.Loading())
    val languagePreference: StateFlow<ResponseState<String>> get() = _languagePreference

    init {
        viewModelScope.launch {
            val language: String? = appPreferencesRepository.getPreferenceStringValue(AppPreferenceConstants.KEY_APP_LANGUAGE, null)
            if (language == null) {
                _languagePreference.value = ResponseState.Error(language, "Invalid language retrieved")
            } else {
                _languagePreference.value = ResponseState.Success(language)
            }
        }
    }

    fun updateLanguagePreference(value: String) {
        viewModelScope.launch { appPreferencesRepository.insertPreferenceStringValue(AppPreferenceConstants.KEY_APP_LANGUAGE,value) }
    }
}