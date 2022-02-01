package com.sg.slightlyred.canberra.livedata.sharedPreferences

import android.content.SharedPreferences
import androidx.lifecycle.LiveData

abstract class SharedPreferencesLiveData<T>(val sharedPreferences: SharedPreferences,
                                            private val key: String,
                                            private val defaultValue: T) : LiveData<T>(), SharedPreferences.OnSharedPreferenceChangeListener {

    abstract fun getValueFromPreferences(key: String, defaultValue: T): T

    override fun onActive() {
        value = getValueFromPreferences(key, defaultValue)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onInactive() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key != null && key == this.key) {
            value = getValueFromPreferences(key, defaultValue)
        }
    }
}