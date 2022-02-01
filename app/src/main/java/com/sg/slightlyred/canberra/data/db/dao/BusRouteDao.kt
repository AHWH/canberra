package com.sg.slightlyred.canberra.data.db.dao

import com.sg.slightlyred.canberra.data.db.ObjectBox
import com.sg.slightlyred.canberra.data.model.bus.BusRoute
import io.objectbox.Box

class BusRouteDao : ObjectBoxDaoImpl<BusRoute>() {
    private var _objectBox: Box<BusRoute>? = null

    override fun getObjectBox(): Box<BusRoute> {
        if (_objectBox == null) {
            _objectBox = ObjectBox.store.boxFor(BusRoute::class.java)
        }
        return _objectBox!!
    }
}