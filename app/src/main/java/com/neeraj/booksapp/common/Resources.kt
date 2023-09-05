package com.neeraj.booksapp.common

import java.lang.Exception

/**
 * @author Neeraj Manchanda
 * This class is designed to wrap the results of network requests and provide a structured way to handle different scenarios
 * such as success, loading, errors, and network-related issues.
 */
/*sealed class Resources<T>(val errorCode : Int? = null, val status : Status? = null, val data: T? = null, val message: String? = null) {

    class Success<T>(data: T?) : Resources<T>(data = data)
    class Loading<T>() : Resources<T>()
    class Error<T>(message: Exception?) : Resources<T>(message = message?.message)
    *//*class InternetError<T>() : Resources<T>(status = Status.INTERNET_ERROR)
    class IOError<T>(code : Int, message: String?) : Resources<T>(errorCode = code, status = Status.NETWORK_ERROR, message = message)*//*
}*/



sealed class Resources<out T> {
    data class Success<out T>(val data: T) : Resources<T>()
    data class Failure(val exception: Exception) : Resources<Nothing>()
    object Loading : Resources<Nothing>()
}
