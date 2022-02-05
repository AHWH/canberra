package com.sg.slightlyred.canberra.view.setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sg.slightlyred.canberra.constants.AppPreferenceConstants
import com.sg.slightlyred.canberra.data.repository.AppPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetupFinalStageViewModel @Inject constructor(private val appPreferencesRepository: AppPreferencesRepository) : ViewModel() {
    fun updateFirstRun() {
        viewModelScope.launch {
            appPreferencesRepository.insertPreferenceBooleanValue(AppPreferenceConstants.KEY_FIRST_RUN, false)
        }
    }
}