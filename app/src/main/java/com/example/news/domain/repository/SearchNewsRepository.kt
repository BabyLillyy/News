package com.example.news.domain.repository

import com.example.news.data.model.Headline

interface SearchNewsRepository {
    suspend fun searchNews(query: String): Headline
}