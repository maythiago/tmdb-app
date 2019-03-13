package com.may.tmdb.movie

import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("genre_ids") val genreId: IntArray,
    @SerializedName("id") val id: Int,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("title") val title: String,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double
) {
    fun withBaseImageUrl(baseUrl: String): MovieModel {
        val newPosterPath = if (posterPath != null && !posterPath.contains(baseUrl)) {
            baseUrl + "w500" + posterPath
        } else {
            posterPath
        }

        val newBackdropPath = if (backdropPath != null && !backdropPath.contains(baseUrl)) {
            baseUrl + "w500" + backdropPath
        } else {
            backdropPath
        }
        return MovieModel(
            newPosterPath,
            adult,
            releaseDate,
            releaseDate,
            genreId,
            id,
            originalTitle,
            originalLanguage,
            title,
            newBackdropPath,
            popularity,
            voteCount,
            video,
            voteAverage
        )
    }
}