package com.sg.slightlyred.canberra.data.db.dao

import android.util.Log
import com.sg.slightlyred.canberra.data.db.ObjectBox
import com.sg.slightlyred.canberra.data.model.LtaDataMallResponse
import com.sg.slightlyred.canberra.data.model.bus.BusStop
import com.sg.slightlyred.canberra.utils.ResponseState
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class BusStopDao : ObjectBoxDaoImpl<BusStop>() {
    private val TAG: String = "BusStopDao"
    private var _objectBox: Box<BusStop>? = null

    override fun getObjectBox(): Box<BusStop> {
        if (_objectBox == null) {
            _objectBox = ObjectBox.store.boxFor()
        }
        return _objectBox!!
    }

    override suspend fun add(t: List<BusStop>) {
        // LTA never provides a way to identify what's added/removed
        // DB may contain inexistent bus stop if we just add
        getObjectBox().store.runInTxAsync({
            getObjectBox().removeAll()
        }, { result: Void?, throwable: Throwable? ->
            getObjectBox().store.runInTxAsync({
                getObjectBox().put(t)
            }, {result2, throwable2 -> Log.i("TAG", "Done")})
        })
    }

    suspend fun addWithStatus(t: List<BusStop>): Boolean {
        // LTA never provides a way to identify what's added/removed
        // DB may contain nonexistent bus stop if we just add
        Log.d(TAG, "addWithStatus() :: Removing existing and inserting ${t.size} bus stops objects")
        return try {
            getObjectBox().removeAll()
            getObjectBox().put(t)
            true
        } catch (ex: Exception) {
            Log.e(TAG, "addWithStatus() :: Encountered error when removing/saving bus stops", ex)
            false
        }
    }
}