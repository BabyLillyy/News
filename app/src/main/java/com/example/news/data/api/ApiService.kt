package com.example.news.data.api

import com.example.news.BuildConfig
import com.example.news.data.model.Headline
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getHeadline(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Headline
    @GET("top-headlines")
    suspend fun searchNews(
        @Query("q") country: String,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Headline
}