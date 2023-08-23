package com.neeraj.booksapp.common

/**
 * @author Neeraj Manchanda
 * This enum class represent the various states of an API call.It defines a set of possible states that an operation can be in,
 * such as loading, success, different types of errors, and internet-related issues.
 */
enum class Status {
    LOADING,
    SUCCESS,
    ERROR,
    NETWORK_ERROR,
    INTERNET_ERROR
}