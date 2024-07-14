package com.example.news.data.repository

import com.example.news.data.model.Headline
import com.example.news.data.repository.datasource.search.SearchNewsRemoteDataSource
import com.example.news.domain.repository.SearchNewsRepository

class SearchNewsRepositoryImpl(
    private val searchNewsRemoteDataSource: SearchNewsRemoteDataSource
): SearchNewsRepository {
    override suspend fun searchNews(query: String): Headline = searchNewsRemoteDataSource.searchNews(query)
}