package com.sg.slightlyred.canberra.data.db.dao

import com.sg.slightlyred.canberra.data.db.ObjectBox
import com.sg.slightlyred.canberra.data.model.bus.BusArrival
import io.objectbox.Box

class BusArrivalDao : ObjectBoxDaoImpl<BusArrival>() {
    private var _objectBox: Box<BusArrival>? = null

    override fun getObjectBox(): Box<BusArrival> {
        if (_objectBox == null) {
            _objectBox = ObjectBox.store.boxFor(BusArrival::class.java)
        }
        return _objectBox!!
    }
}