package com.sg.slightlyred.canberra

import android.app.Application
import android.util.Log
import com.sg.slightlyred.canberra.data.db.ObjectBox
import com.sg.slightlyred.canberra.data.model.bus.MyObjectBox
import dagger.hilt.android.HiltAndroidApp
import io.objectbox.android.AndroidObjectBrowser

@HiltAndroidApp
class CanberraApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }
}