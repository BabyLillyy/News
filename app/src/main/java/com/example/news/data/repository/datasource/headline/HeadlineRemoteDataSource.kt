package com.example.news.data.repository.datasource.headline

import com.example.news.data.model.Headline

interface HeadlineRemoteDataSource {
    suspend fun getHeadline(country: String): Headline
}