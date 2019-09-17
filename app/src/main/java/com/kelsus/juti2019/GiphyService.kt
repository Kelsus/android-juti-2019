package com.kelsus.juti2019

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyService {

    @GET("gifs/random?apikey=${ApiClient.APIKEY}")
    suspend fun getRandomGif(@Query("tag") tag: String?, @Query("random_id") randomId: String): Response<GiphyResponse>
}