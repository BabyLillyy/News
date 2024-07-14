package com.example.news.data.repository

import com.example.news.data.model.Headline
import com.example.news.data.repository.datasource.headline.HeadlineRemoteDataSource
import com.example.news.domain.repository.HeadlineRepository
import javax.inject.Inject

class HeadlineRepositoryImpl @Inject constructor(
    private val headlineRemoteDatasource: HeadlineRemoteDataSource
) : HeadlineRepository {
    override suspend fun retrieveHeadline(country: String): Headline =
        headlineRemoteDatasource.getHeadline(country)
}