package com.application.newsappgolapbarman.repository.service

import com.application.newsappgolapbarman.model.newsResponse
import com.application.newsappgolapbarman.utils.constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country")country:String ="in",
        @Query("language")language:String ="en",
        @Query("page")pageNumber:Int,
        @Query("apiKey")apiKey:String =constants.API_KEY
    ): Response<newsResponse>

    @GET("v2/everything")
    suspend fun getSearchNews(
        @Query("q")searchQuery:String,
        @Query("language")language:String ="en",
        @Query("page")pageNumber:Int,
        @Query("apiKey")apiKey:String =constants.API_KEY
    ): Response<newsResponse>

}