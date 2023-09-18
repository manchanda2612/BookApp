package com.neeraj.common

import java.lang.Exception

/**
 * @author Neeraj Manchanda
 * This class is designed to wrap the results of network requests and provide a structured way to handle different scenarios
 * such as success, loading, errors, and network-related issues.
 */
sealed class Resources<out T> {
    data class Success<out T>(val data: T) : Resources<T>()
    data class Failure(val exception: Exception) : Resources<Nothing>()
    object Loading : Resources<Nothing>()
}
