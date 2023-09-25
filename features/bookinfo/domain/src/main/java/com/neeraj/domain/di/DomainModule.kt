package com.neeraj.domain.di

import com.neeraj.domain.respository.BookRepository
import com.neeraj.domain.usecases.GetBookDetailUseCase
import com.neeraj.domain.usecases.GetBookListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


/**
 * @author Neeraj Manchanda
 *
 * This class provide dependencies related to the domain layer.
 */
@InstallIn(ViewModelComponent::class)
@Module
class DomainModule {

    @Provides
    fun provideGetBookListUseCase(bookRepository: BookRepository) = GetBookListUseCase(bookRepository)

    @Provides
    fun provideGetBookDetailUseCase(bookRepository: BookRepository) = GetBookDetailUseCase(bookRepository)


}