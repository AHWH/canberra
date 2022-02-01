package com.sg.slightlyred.canberra.data.db.dao

import io.objectbox.Box
import io.objectbox.android.ObjectBoxLiveData
import io.objectbox.query.Query

interface ObjectBoxDao<T> {
    fun getObjectBox(): Box<T>
    fun getQuery(): Query<T>
    suspend fun add(t: T): Long
    suspend fun add(t: List<T>)
    suspend fun getOne(t: Long): T
    suspend fun getAll(): List<T>
    suspend fun remove(t: Long): Boolean
    suspend fun remove(t: List<Long>)
    suspend fun removeAll()
    suspend fun getCount(): Long
}