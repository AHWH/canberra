package com.sg.slightlyred.canberra.utils

sealed class ResponseState<out T>(val status: Status, val data: T?, val message: String?) {
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    class Success<T>(data: T): ResponseState<T>(Status.SUCCESS, data, null)
    class Error<T>(data: T?, message: String): ResponseState<T>(Status.ERROR, data, message)
    class Loading<T>(): ResponseState<T>(Status.LOADING, null, null)
}