package com.neeraj.data.di


import com.neeraj.common.InternetUtil
import com.neeraj.data.mapper.BookMapper
import com.neeraj.data.network.ApiService
import com.neeraj.data.repository.BookRepositoryImpl
import com.neeraj.domain.respository.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.neeraj.data.BuildConfig
import javax.inject.Singleton


/**
 * @author Neeraj Manchanda
 * This module is responsible for providing instances of various classes, such as an ApiService and a BookRepository,
 * that can be injected into other parts of app.
 */
@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
    }
    @Provides
    fun provideBookRepository(apiService: ApiService, bookMapper: BookMapper, internetUtil: InternetUtil) : BookRepository {
        return BookRepositoryImpl(apiService, bookMapper, internetUtil)
    }
}