package com.kelsus.juti2019.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    const val BASE_URL = "https://api.giphy.com/v1/"
    val client: Retrofit

    const val APIKEY = "30ZJPr1Qsoi1u4BLkJxLVZdhu2LYDq0h"

    init {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        client = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build()
    }
}