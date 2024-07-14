package com.example.news.presentation.di.module

import com.example.news.domain.repository.HeadlineRepository
import com.example.news.domain.usecase.GetHeadlineUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetHeadlineUseCase(headlineRepository: HeadlineRepository): GetHeadlineUseCase {
        return GetHeadlineUseCase(headlineRepository)
    }

}