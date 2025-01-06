package com.koushik.core.di

import com.koushik.core.util.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    // Provides NetworkHelper
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return NetworkHelper.provideRetrofit()
    }
}