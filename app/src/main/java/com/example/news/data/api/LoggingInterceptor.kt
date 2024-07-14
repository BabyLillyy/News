package com.example.news.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url
        val method = request.method
        println("OkHttp, Request --> method: $method")
        println("OkHttp, Request --> url: $url")

        val response = chain.proceed(request)
        val code = response.code
        val responseBody = response.peekBody(Long.MAX_VALUE)
        println("OkHttp,\n\n")
        println("OkHttp, Response --> code: $code")
        println("OkHttp, Response --> body: ${responseBody.string()}")

        return response
    }

    companion object {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor())
            .build()
    }
}
