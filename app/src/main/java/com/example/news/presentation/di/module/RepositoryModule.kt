package com.example.news.presentation.di.module

import com.example.news.data.repository.HeadlineRepositoryImpl
import com.example.news.data.repository.SearchNewsRepositoryImpl
import com.example.news.data.repository.datasource.headline.HeadlineRemoteDataSource
import com.example.news.data.repository.datasource.search.SearchNewsRemoteDataSource
import com.example.news.domain.repository.HeadlineRepository
import com.example.news.domain.repository.SearchNewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideHeadlineRepository(
        headlineRemoteDataSource: HeadlineRemoteDataSource
    ): HeadlineRepository =
        HeadlineRepositoryImpl(headlineRemoteDataSource)


    @Singleton
    @Provides
    fun provideSearchRepository(
        searchRemoteDataSource: SearchNewsRemoteDataSource
    ): SearchNewsRepository = SearchNewsRepositoryImpl(searchRemoteDataSource)
}