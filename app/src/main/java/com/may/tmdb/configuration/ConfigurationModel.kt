package com.may.tmdb.configuration

import com.google.gson.annotations.SerializedName

data class ConfigurationModel(
    @SerializedName("images") val images: ConfigurationImageModel
)