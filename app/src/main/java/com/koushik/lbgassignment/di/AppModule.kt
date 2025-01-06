package com.koushik.lbgassignment.di

import com.koushik.domain.usecase.GetItemsUseCase
import com.koushik.lbgassignment.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provides MainViewModel
    @Provides
    @Singleton
    fun provideMainViewModel(getItemsUseCase: GetItemsUseCase): MainViewModel {
        return MainViewModel(getItemsUseCase)
    }
}