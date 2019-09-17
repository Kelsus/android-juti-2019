package com.kelsus.juti2019

import java.util.*

class ApiRepository {
    private val apiService = ApiClient.getClient().create(GiphyService::class.java)

    suspend fun getRandomGif(tag: String?): ApiResult<GiphyImage, String> {
        try {
            val response = apiService.getRandomGif(tag, UUID.randomUUID().toString())
            if (response.isSuccessful) {
                if (response.body()?.data?.images?.fixedHeight != null) {
                    return ApiResult.Success(response.body()?.data?.images?.fixedHeight!!)
                } else {
                    return ApiResult.NonSuccess("Empty body", response.code())
                }
            } else {
                return ApiResult.NonSuccess("Error", response.code())
            }
        } catch (e: Exception) {
            return ApiResult.Failure(e)
        }
    }

}