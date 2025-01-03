package com.koushik.data.di

import com.koushik.data.repository.NetworkRepository
import com.koushik.domain.repository.ItemRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ItemRepositoryBindingModule {

    // Bind ItemRepository to NetworkRepository
    @Binds
    @Singleton
    abstract fun bindItemRepository(networkRepository: NetworkRepository): ItemRepository
}