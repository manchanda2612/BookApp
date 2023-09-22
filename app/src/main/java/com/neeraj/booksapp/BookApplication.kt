package com.neeraj.booksapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/**
 * @author Neeraj Manchanda
 */
@Suppress("unused")
@HiltAndroidApp
class BookApplication : Application() {
    init {
        instance = this
    }

    companion object {

        private var instance: BookApplication? = null

        fun applicationContext(): BookApplication {
            return instance as BookApplication
        }
    }
}