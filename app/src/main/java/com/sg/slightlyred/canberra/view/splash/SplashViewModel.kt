package com.sg.slightlyred.canberra.view.splash

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
class SplashViewModel @Inject constructor(private val appPreferencesRepository: AppPreferencesRepository) : ViewModel() {
    private val _isFirstRun: MutableStateFlow<ResponseState<Boolean>> = MutableStateFlow(ResponseState.Loading())
    val isFirstRun: StateFlow<ResponseState<Boolean>> = _isFirstRun

    init {
        viewModelScope.launch() {
            _isFirstRun.value = ResponseState.Success(appPreferencesRepository.getPreferenceBooleanValue(AppPreferenceConstants.KEY_FIRST_RUN, true))
        }
    }


}