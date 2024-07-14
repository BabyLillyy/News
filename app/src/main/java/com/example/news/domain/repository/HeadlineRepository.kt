package com.example.news.domain.repository

import com.example.news.data.model.Headline

interface HeadlineRepository {
    suspend fun retrieveHeadline(country: String): Headline
}