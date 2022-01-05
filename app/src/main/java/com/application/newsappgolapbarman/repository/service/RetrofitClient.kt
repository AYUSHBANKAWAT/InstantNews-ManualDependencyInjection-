package com.application.newsappgolapbarman.repository.service

import com.application.newsappgolapbarman.utils.constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object{
        private val retrofitClient by lazy {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
           Retrofit.Builder()
               .client(client)
               .baseUrl(constants.BASE_URL)
               .addConverterFactory(GsonConverterFactory.create())
               .build()
        }
        val api by lazy {
            retrofitClient.create(NewsApi::class.java)
        }
    }
}