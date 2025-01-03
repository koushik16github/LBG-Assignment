package com.koushik.domain.di

import com.koushik.data.repository.NetworkRepository
import com.koushik.domain.usecase.GetItemsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    // Provides GetItemsUseCase
    @Provides
    @Singleton
    fun provideGetItemsUseCase(repository: NetworkRepository): GetItemsUseCase {
        return GetItemsUseCase(repository)
    }
}