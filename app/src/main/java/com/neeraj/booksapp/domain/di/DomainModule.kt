package com.neeraj.booksapp.domain.di

import com.neeraj.booksapp.domain.respository.BookRepository
import com.neeraj.booksapp.domain.use_cases.GetBookListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


/**
 * @author Neeraj Manchanda
 * It provides an instance of a GetBookListUseCase class using the BookRepository as a dependency.
 */
@InstallIn(SingletonComponent::class)
@Module
object DomainModule {

    @Provides
    fun provideGetBookListUseCase(bookRepository: BookRepository) : GetBookListUseCase {
        return GetBookListUseCase(bookRepository)
    }
}