package com.example.news.data.model

import com.google.gson.annotations.SerializedName
data class Headline(
    @SerializedName("status") var status: String? = null,
    @SerializedName("totalResults") var totalResults: Int? = null,
    @SerializedName("articles") var articles: ArrayList<Articles> = arrayListOf()
)
