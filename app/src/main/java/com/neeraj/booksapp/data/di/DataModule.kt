package com.neeraj.booksapp.data.di

import com.neeraj.booksapp.BuildConfig
import com.neeraj.booksapp.data.mapper.BookMapper
import com.neeraj.booksapp.data.network.ApiService
import com.neeraj.booksapp.data.repository.BookRepositoryImpl
import com.neeraj.booksapp.domain.respository.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * @author Neeraj Manchanda
 * This module is responsible for providing instances of various classes, such as an ApiService and a BookRepository,
 * that can be injected into other parts of app.
 */
@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
    }



    @Provides
    fun provideBookRepository(apiService: ApiService, bookMapper: BookMapper) : BookRepository {
        return BookRepositoryImpl(apiService, bookMapper)
    }
}