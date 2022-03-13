package com.sg.slightlyred.canberra.data.db.dao

import android.util.Log
import com.sg.slightlyred.canberra.data.db.ObjectBox
import com.sg.slightlyred.canberra.data.model.bus.BusRoute
import io.objectbox.Box

class BusRouteDao : ObjectBoxDaoImpl<BusRoute>() {
    private val TAG: String = "BusRouteDao"
    private var _objectBox: Box<BusRoute>? = null

    override fun getObjectBox(): Box<BusRoute> {
        if (_objectBox == null) {
            _objectBox = ObjectBox.store.boxFor(BusRoute::class.java)
        }
        return _objectBox!!
    }

    override suspend fun add(t: List<BusRoute>) {
        // LTA never provides a way to identify what's added/removed
        // DB may contain inexistent bus stop if we just add
        getObjectBox().store.runInTxAsync({
            getObjectBox().removeAll()
        }, { result: Void?, throwable: Throwable? ->
            getObjectBox().store.runInTxAsync({
                getObjectBox().put(t)
            }, {result2, throwable2 -> Log.i(TAG, "add() :: Done")})
        })
    }

    suspend fun addWithStatus(t: List<BusRoute>): Boolean {
        // LTA never provides a way to identify what's added/removed
        // DB may contain nonexistent bus stop if we just add
        Log.d(TAG, "addWithStatus() :: Removing existing and inserting ${t.size} bus service objects")
        return try {
            getObjectBox().removeAll()
            getObjectBox().put(t)
            true
        } catch (ex: Exception) {
            Log.e(TAG, "addWithStatus() :: Encountered error when removing/saving bus service", ex)
            false
        }
    }
}