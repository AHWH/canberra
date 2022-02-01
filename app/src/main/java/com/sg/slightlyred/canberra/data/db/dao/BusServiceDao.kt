package com.sg.slightlyred.canberra.data.db.dao

import com.sg.slightlyred.canberra.data.db.ObjectBox
import com.sg.slightlyred.canberra.data.model.bus.BusService
import io.objectbox.Box

class BusServiceDao : ObjectBoxDaoImpl<BusService>(){
    private var _objectBox: Box<BusService>? = null

    override fun getObjectBox(): Box<BusService> {
        if (_objectBox == null) {
            _objectBox = ObjectBox.store.boxFor(BusService::class.java)
        }
        return _objectBox!!
    }
}