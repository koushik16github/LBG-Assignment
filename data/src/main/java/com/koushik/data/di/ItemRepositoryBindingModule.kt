package com.koushik.data.di

import com.koushik.data.repository.NewsRepository
import com.koushik.domain.repository.ItemRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ItemRepositoryBindingModule {

    // Bind ItemRepository to NewsRepository
    @Binds
    @Singleton
    abstract fun bindItemRepository(newsRepository: NewsRepository): ItemRepository
}