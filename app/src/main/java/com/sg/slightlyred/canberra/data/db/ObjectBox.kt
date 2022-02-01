package com.sg.slightlyred.canberra.data.db

import android.content.Context
import com.sg.slightlyred.canberra.data.model.bus.MyObjectBox
import io.objectbox.BoxStore

object ObjectBox {
    lateinit var store: BoxStore
        private set

    fun init(context: Context) {
        store = MyObjectBox.builder().androidContext(context).build()
    }
}