package com.koushik.lbgassignment.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.koushik.core.util.NetworkHelper
import com.koushik.data.api.ApiService
import com.koushik.data.repository.NetworkRepository
import com.koushik.domain.usecase.GetItemsUseCase
import com.koushik.lbgassignment.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provides NetworkHelper
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return NetworkHelper.provideRetrofit()
    }

    // Provides ApiService
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    // Provides NetworkRepository
    @Provides
    @Singleton
    fun provideNetworkRepository(apiService: ApiService): NetworkRepository {
        return NetworkRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideMainViewModel(getItemsUseCase: GetItemsUseCase): MainViewModel {
        return MainViewModel(getItemsUseCase)
    }

    // Provides GetItemsUseCase
    @Provides
    @Singleton
    fun provideGetItemsUseCase(repository: NetworkRepository): GetItemsUseCase {
        return GetItemsUseCase(repository)
    }

    // Optionally provides Gson instance for any custom serialization
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }
}