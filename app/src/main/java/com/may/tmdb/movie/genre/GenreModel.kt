package com.may.tmdb.movie.genre

import com.google.gson.annotations.SerializedName

class GenreModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)
data class GenreResponseModel(@SerializedName("genres") val genres: Array<GenreModel>)