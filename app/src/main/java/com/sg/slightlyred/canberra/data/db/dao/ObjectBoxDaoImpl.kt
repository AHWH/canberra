package com.sg.slightlyred.canberra.data.db.dao

import io.objectbox.query.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class ObjectBoxDaoImpl<T> : ObjectBoxDao<T> {
    private var _query: Query<T>? = null

    override fun getQuery(): Query<T> {
        if (_query == null) {
            _query = getObjectBox().query().build()
        }
        return _query!!
    }

    override suspend fun add(t: T): Long {
        return getObjectBox().put(t)
    }

    override suspend fun add(t: List<T>) {
        getObjectBox().put(t)
    }

    override suspend fun getOne(t: Long): T {
        return getObjectBox()[t]
    }

    override suspend fun getAll(): List<T> {
        return getObjectBox().all
    }

    override suspend fun remove(t: Long): Boolean {
        return getObjectBox().remove(t)
    }

    override suspend fun remove(t: List<Long>) {
        getObjectBox().removeByIds(t)
    }

    override suspend fun removeAll() {
        getObjectBox().removeAll()
    }

    override suspend fun getCount(): Long {
        return getObjectBox().count()
    }
}