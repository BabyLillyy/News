package com.example.news.presentation.di.module

import com.example.news.data.api.ApiService
import com.example.news.data.repository.datasource.headline.HeadlineRemoteDataSource
import com.example.news.data.repository.datasource.search.SearchNewsRemoteDataSource
import com.example.news.data.repository.datasourceimpl.headline.HeadlineRemoteDataSourceImpl
import com.example.news.data.repository.datasourceimpl.search.SearchNewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataSourceModule {

    @Singleton
    @Provides
    fun provideHeadlineRemoteDataSource(apiService: ApiService): HeadlineRemoteDataSource =
        HeadlineRemoteDataSourceImpl(apiService)

    @Singleton
    @Provides
    fun provideSearchNewsRemoteDataSource(apiService: ApiService): SearchNewsRemoteDataSource =
        SearchNewsRemoteDataSourceImpl(apiService)
}