package com.may.tmdb.movie.upcoming

import com.google.gson.annotations.SerializedName
import com.may.tmdb.movie.MovieModel
import java.util.*

class MovieModelBuilder {
    private val posterPath: String? = null
    private val adult: Boolean = false
    private val overview: String = ""
    private val releaseDate: String = ""
    private val genreId: IntArray = intArrayOf()
    private val id: Int = System.currentTimeMillis().toInt()
    private val originalTitle: String = ""
    private val originalLanguage: String = ""
    private val title: String = ""
    private val backdropPath: String? = null
    private val popularity: Double = 0.0
    private val voteCount: Int = 0
    private val video: Boolean = false
    private val voteAverage: Double = 0.0

    fun build(): MovieModel {
        return MovieModel(
            posterPath, adult, overview, releaseDate, genreId, id, originalTitle, originalLanguage, title,
            backdropPath, popularity, voteCount, video, voteAverage
        )
    }

}
