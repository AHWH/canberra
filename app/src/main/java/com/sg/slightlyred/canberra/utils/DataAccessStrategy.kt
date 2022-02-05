package com.sg.slightlyred.canberra.utils

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object DataAccessStrategy {
    fun <T> performGet(
        databaseQuery: (suspend () -> T)? = null,
        callState: (suspend () -> ResponseState<T>)? = null,
        saveCallResult: (suspend (T) -> Unit)? = null
    ): Flow<ResponseState<T>> {
        if (databaseQuery == null) {
            return performGetRemoteOnly(callState!!, saveCallResult!!)
        } else if (callState == null) {
            return performGetDatabaseOnly(databaseQuery)
        } else {
            return performGetDefault(databaseQuery, callState, saveCallResult!!)
        }
    }

    private fun <T> performGetDefault(
        databaseQuery: suspend () -> T,
        callState: suspend () -> ResponseState<T>,
        saveCallResult: suspend (T) -> Unit
    ) : Flow<ResponseState<T>> = flow {
        // ^ LiveData Co-routine
        emit(ResponseState.Loading())
        // invoke is kotlin function to execute the function (passed in as parameter)
        val dbSource: T = databaseQuery.invoke()
        emit(ResponseState.Success(dbSource))

        when (val responseStatus = callState.invoke()) {
            is ResponseState.Success -> {
                saveCallResult(responseStatus.data!!)
                emit(ResponseState.Success(responseStatus.data))
            }
            is ResponseState.Error -> { emit(ResponseState.Error(null, responseStatus.message!!)) }
            else -> {}
        }
    }.flowOn(Dispatchers.IO)

    private fun <T> performGetRemoteOnly(
        callState: suspend () -> ResponseState<T>,
        saveCallResult: suspend (T) -> Unit
    ): Flow<ResponseState<T>> = flow {
        // ^ LiveData Co-routine
        emit(ResponseState.Loading())
        when (val responseStatus = callState.invoke()) {
            is ResponseState.Success -> {
                saveCallResult(responseStatus.data!!)
                emit(ResponseState.Success(responseStatus.data))
            }
            is ResponseState.Error -> { emit(ResponseState.Error(null, responseStatus.message!!)) }
            else -> {}
        }
    }

    private fun <T> performGetDatabaseOnly(
        databaseQuery: suspend () -> T
    ): Flow<ResponseState<T>> = flow {
        // ^ LiveData Co-routine
        emit(ResponseState.Loading())
        // invoke is kotlin function to execute the function (passed in as parameter)
        val dbSource: T = databaseQuery.invoke()
        emit(ResponseState.Success(dbSource))
    }

    fun <T> performGetRemoteAndSaveOnly(
        callState: suspend () -> ResponseState<T>,
        saveCallResult: suspend (T) -> Boolean
    ): Flow<ResponseState<Boolean>> = flow {
        emit(ResponseState.Loading())
        when (val responseStatus = callState.invoke()) {
            is ResponseState.Success -> {
                if (saveCallResult(responseStatus.data!!)) {
                    emit(ResponseState.Success(true))
                } else {
                    emit(ResponseState.Error(false, ""))
                }
            }
            is ResponseState.Error -> { emit(ResponseState.Error(null, responseStatus.message!!)) }
            else -> {}
        }
    }.flowOn(Dispatchers.IO)
}