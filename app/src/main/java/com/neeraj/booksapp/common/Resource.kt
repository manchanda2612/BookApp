package com.neeraj.booksapp.common

/**
 * @author Neeraj Manchanda
 * This class is designed to wrap the results of network requests and provide a structured way to handle different scenarios
 * such as success, loading, errors, and network-related issues.
 */
sealed class Resource<T>(val errorCode : Int? = null,  val status : Status, val data: T? = null, val message: String? = null) {

    class Success<T>(data: T?) : Resource<T>(status = Status.SUCCESS, data = data)
    class Loading<T>() : Resource<T>(status = Status.LOADING)
    class Error<T>(message: String?) : Resource<T>(status = Status.ERROR, message = message)
    class InternetError<T>() : Resource<T>(status = Status.INTERNET_ERROR)
    class IOError<T>(code : Int, message: String?) : Resource<T>(errorCode = code, status = Status.NETWORK_ERROR, message = message)
}
