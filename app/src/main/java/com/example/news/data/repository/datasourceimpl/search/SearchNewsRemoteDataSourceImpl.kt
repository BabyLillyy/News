package com.example.news.data.repository.datasourceimpl.search

import com.example.news.data.api.ApiService
import com.example.news.data.model.Headline
import com.example.news.data.repository.datasource.headline.HeadlineRemoteDataSource
import com.example.news.data.repository.datasource.search.SearchNewsRemoteDataSource
import javax.inject.Inject

class SearchNewsRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : SearchNewsRemoteDataSource {
    override suspend fun searchNews(quote: String): Headline = apiService.searchNews(quote)
}