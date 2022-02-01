package com.sg.slightlyred.canberra.service

import android.util.Log
import com.sg.slightlyred.canberra.utils.ResponseState
import retrofit2.Response

abstract class RemoteSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): ResponseState<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return ResponseState.Success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): ResponseState<T> {
        Log.e("remoteDataSource", message)
        return ResponseState.Error(null,"Network call has failed for a following reason: $message")
    }
}