package com.sg.slightlyred.canberra.data.db.dao

import com.sg.slightlyred.canberra.data.db.ObjectBox
import com.sg.slightlyred.canberra.data.model.bus.NextBus
import io.objectbox.Box

class NextBusDao : ObjectBoxDaoImpl<NextBus>() {
    private var _objectBox: Box<NextBus>? = null

    override fun getObjectBox(): Box<NextBus> {
        if (_objectBox == null) {
            _objectBox = ObjectBox.store.boxFor(NextBus::class.java)
        }
        return _objectBox!!
    }
}