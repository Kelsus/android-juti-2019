package com.kelsus.juti2019.network

import com.kelsus.juti2019.model.GiphyImage
import java.util.*

class ApiRepository {

    private val apiService = ApiClient.client.create(GiphyService::class.java)

    suspend fun getRandomGif(tag: String): ApiResult<GiphyImage, String> {
        try {
            val response = apiService.getRandomGif(tag, UUID.randomUUID().toString())
            if (response.isSuccessful) {
                response.body()?.data?.images?.fixedHeight?.let {
                    return ApiResult.Success(it)
                }
                return ApiResult.NonSuccess("Sin resultados")
            } else {
                return ApiResult.NonSuccess(response.errorBody()?.string() ?: "Sin mensaje de error")
            }
        } catch (e: Exception) {
            return ApiResult.Failure(e)
        }
    }
}