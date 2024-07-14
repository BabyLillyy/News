package com.example.news.data.repository.datasource.search

import com.example.news.data.model.Headline

interface SearchNewsRemoteDataSource {
    suspend fun searchNews(quote: String): Headline
}