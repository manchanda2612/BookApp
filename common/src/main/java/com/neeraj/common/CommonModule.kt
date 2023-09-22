package com.neeraj.common

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 *
 * @author Neeraj Manchanda
 * Provides an instance of the Android Application Context.
 * This function is used to provide the Android Application Context to other parts of the application
 * that require access to the application-level context.
 */
@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Provides
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    fun provideInternetUtil(context: Context): InternetUtil {
        return InternetUtil(context)
    }
}