package com.sg.slightlyred.canberra.data.db.dao

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.preference.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppPreferencesDao(private val appContext: Context) {
    private val TAG: String = AppPreferencesDao::class.java.name

    private var _sharedPreferences: SharedPreferences? = null
    private val sharedPreferences: SharedPreferences
        get() {
            if (_sharedPreferences == null) {
                _sharedPreferences = PreferenceManager.getDefaultSharedPreferences(appContext)
            }
            return _sharedPreferences!!
        }
    private var _sharedPreferencesEditor: SharedPreferences.Editor? = null
    private val sharedPreferencesEditor: SharedPreferences.Editor
        get() {
            if (_sharedPreferencesEditor == null) {
                _sharedPreferencesEditor = sharedPreferences.edit()
            }
            return _sharedPreferencesEditor!!
        }

    // Getter
    suspend fun getBoolean(key: String, defaultValue: Boolean): Boolean = withContext(Dispatchers.IO) {
        Log.d(TAG, "Getting boolean value of preference: $key")
        sharedPreferences.getBoolean(key, defaultValue)
    }

    suspend fun getString(key: String, defaultValue: String?): String? = withContext(Dispatchers.IO) {
        Log.d(TAG, "Getting string value of preference: $key")
        sharedPreferences.getString(key, defaultValue)
    }

    // Setter
    suspend fun insertString(key: String, value: String): Boolean = withContext(Dispatchers.IO) {
        Log.d(TAG, "Inserting string value of preference: $key with value: $value")
        sharedPreferencesEditor.putString(key, value)
        sharedPreferencesEditor.commit()
    }

    suspend fun insertBoolean(key: String, value: Boolean): Boolean = withContext(Dispatchers.IO) {
        Log.d(TAG, "Inserting boolean value of preference: $key with value: $value")
        sharedPreferencesEditor.putBoolean(key, value)
        sharedPreferencesEditor.commit()
    }
}