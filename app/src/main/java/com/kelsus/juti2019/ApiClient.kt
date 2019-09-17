package com.kelsus.juti2019

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    const val BASE_URL = "https://api.giphy.com/v1/"
    var retrofit: Retrofit? = null

    const val APIKEY = "30ZJPr1Qsoi1u4BLkJxLVZdhu2LYDq0h"

    fun getClient(): Retrofit {
        if (retrofit == null) {
            val builder = OkHttpClient.Builder()
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build()
        }
        return retrofit as Retrofit
    }

}