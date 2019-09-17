package com.kelsus.juti2019

import com.google.gson.annotations.SerializedName

data class GiphyImages(
    @SerializedName("downsized_large") val downsizedLarge: GiphyImage,
    @SerializedName("fixed_height") val fixedHeight: GiphyImage
)