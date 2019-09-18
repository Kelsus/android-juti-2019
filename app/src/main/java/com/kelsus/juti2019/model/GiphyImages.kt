package com.kelsus.juti2019.model

import com.google.gson.annotations.SerializedName

class GiphyImages {
    @SerializedName("fixed_height_still")
    val fixedHeightStill: GiphyImage? = null

    @SerializedName("downsized_large")
    val downsizedLarge: GiphyImage? = null

    @SerializedName("fixed_height")
    val fixedHeight: GiphyImage? = null
}