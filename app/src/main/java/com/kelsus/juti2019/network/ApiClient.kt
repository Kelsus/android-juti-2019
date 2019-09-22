package com.kelsus.juti2019.network

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.kelsus.juti2019.model.GiphyData
import com.kelsus.juti2019.model.GiphyResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

object ApiClient {
    const val BASE_URL = "https://api.giphy.com/v1/"
    val client: Retrofit

    const val APIKEY = "30ZJPr1Qsoi1u4BLkJxLVZdhu2LYDq0h"

    init {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        val gson = GsonBuilder()
        gson.registerTypeAdapter(GiphyResponse::class.java, CustomDeserializer())

        client = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson.create()))
                .client(builder.build())
                .build()
    }
}

class CustomDeserializer : JsonDeserializer<GiphyResponse> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): GiphyResponse? {
        try {
            val giphyResponse = GiphyResponse()
            val dataJson = json?.asJsonObject!!["data"].asJsonObject
            val data = context?.deserialize<GiphyData>(dataJson, GiphyData::class.java)
            giphyResponse.data = data
            return giphyResponse
        } catch (e: Exception) {
            return null
        }
    }
}