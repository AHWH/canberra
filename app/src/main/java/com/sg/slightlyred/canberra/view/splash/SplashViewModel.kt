package com.sg.slightlyred.canberra.view.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.preference.PreferenceManager
import com.sg.slightlyred.canberra.constants.AppPreferenceConstants
import com.sg.slightlyred.canberra.livedata.sharedPreferences.SharedPreferencesBooleanLiveData

class SplashViewModel(app: Application) : AndroidViewModel(app) {
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplication<Application>().applicationContext)

    private val isFirstRun: LiveData<Boolean> by lazy {
        SharedPreferencesBooleanLiveData(sharedPreferences, AppPreferenceConstants.KEY_FIRST_RUN, true)
    }

    fun getIsFirstRun() : LiveData<Boolean> {
        return isFirstRun
    }
}