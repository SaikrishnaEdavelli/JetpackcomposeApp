package com.sample.chaitanyasampleapp.data.di

import com.sample.chaitanyasampleapp.BuildConfig
import com.sample.chaitanyasampleapp.data.api.NewsApiService
import com.sample.chaitanyasampleapp.data.repository.NewsRepository
import com.sample.chaitanyasampleapp.data.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService = retrofit.create(NewsApiService::class.java)

    @Provides
    @Singleton
    fun provideNewsRepository(apiService: NewsApiService): NewsRepository = NewsRepositoryImpl(apiService)
}
