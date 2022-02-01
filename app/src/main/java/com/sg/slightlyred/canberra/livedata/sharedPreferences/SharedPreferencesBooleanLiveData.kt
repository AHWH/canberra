package com.sg.slightlyred.canberra.livedata.sharedPreferences

import android.content.SharedPreferences

class SharedPreferencesBooleanLiveData(sharedPreferences: SharedPreferences, key: String,
                                       defaultValue: Boolean) : SharedPreferencesLiveData<Boolean>(sharedPreferences, key, defaultValue) {

    override fun getValueFromPreferences(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }
}