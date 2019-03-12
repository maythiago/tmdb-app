package com.may.tmdb.configuration

import com.google.gson.annotations.SerializedName

data class ConfigurationImageModel(
    @SerializedName("base_url") val baseUrl: String,
    @SerializedName("poster_sizes") val posterSizes: Array<String>
)