package com.example.dictionaryapp.util

sealed class Resource<T>(val data: T? = null, val message: String? = null) {

    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[message=$message, data=$data]"
            is Loading -> "Loading[data=$data]"
        }
    }
}
