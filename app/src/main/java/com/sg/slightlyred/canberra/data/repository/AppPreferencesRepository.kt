package com.sg.slightlyred.canberra.data.repository

import com.sg.slightlyred.canberra.data.db.dao.AppPreferencesDao
import javax.inject.Inject

class AppPreferencesRepository @Inject constructor(private val appPreferencesDao: AppPreferencesDao) {

    // One-shot requests -> Not necessary to use Flow here
    // Suspend on UI thread here to quickly return values
    // Dao level will suspend on IO level
    suspend fun getPreferenceBooleanValue(key: String, defaultValue: Boolean?): Boolean {
        if (defaultValue == null) {
            return appPreferencesDao.getBoolean(key, false)
        }
        return appPreferencesDao.getBoolean(key, defaultValue)
    }

    suspend fun getPreferenceStringValue(key: String, defaultValue: String?): String? {
        return appPreferencesDao.getString(key, defaultValue)
    }

    suspend fun insertPreferenceStringValue(key: String, value: String): Boolean {
        return appPreferencesDao.insertString(key, value)
    }

    suspend fun insertPreferenceBooleanValue(key: String, value: Boolean): Boolean {
        return appPreferencesDao.insertBoolean(key, value)
    }
}