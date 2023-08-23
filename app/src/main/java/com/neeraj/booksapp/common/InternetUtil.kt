package com.neeraj.booksapp.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.neeraj.booksapp.BookApplication

/**
 * @author Neeraj Manchanda
 * Network Utility class that helps to check whether user has internet connection or not,
 * before performing network-related operations, such as making API calls.
 */
class InternetUtil {

    companion object {

        fun isInternetAvailable(): Boolean {
            val result: Boolean
            val connectivityManager = BookApplication
                .applicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
            return result
        }
    }
}