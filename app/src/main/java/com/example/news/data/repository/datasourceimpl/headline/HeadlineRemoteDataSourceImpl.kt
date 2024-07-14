package com.example.news.data.repository.datasourceimpl.headline

import com.example.news.data.api.ApiService
import com.example.news.data.model.Headline
import com.example.news.data.repository.datasource.headline.HeadlineRemoteDataSource
import javax.inject.Inject

class HeadlineRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : HeadlineRemoteDataSource {
    override suspend fun getHeadline(country: String): Headline = apiService.getHeadline(country)
}