package com.notes.taskapp.retrofit.utils

sealed class ApiState<out T> {
    class Success<out R>(val data: R) : ApiState<R>()
    class Failure(val msg: Throwable) : ApiState<Nothing>()
    object Loading : ApiState<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success $data"
            is Failure -> "Failure ${msg.message}"
            Loading -> "Loading"
        }
    }
}