package com.may.tmdb.movie.upcoming

import com.may.tmdb.movie.MovieModel

class MovieModelBuilder {
    private var posterPath: String? = null
    private var mLargePosterPath: String? = null
    private var mThumbnailPosterPath: String? = null
    private var mReleaseDate = ""
    private val id = System.currentTimeMillis().toInt()
    private var mTitle = ""
    private var mGenres = intArrayOf()
    private var mOverview = ""

    fun build(): MovieModel {
        return MovieModel(
            posterPath,
            mReleaseDate,
            id,
            mTitle,
            mOverview,
            mGenres,
            mLargePosterPath,
            mThumbnailPosterPath
        )
    }

    fun thumbnailPosterPath(url: String): MovieModelBuilder {
        mThumbnailPosterPath = url
        return this
    }

    fun title(title: String): MovieModelBuilder {
        mTitle = title
        return this
    }

    fun releaseDate(releaseDate: String): MovieModelBuilder {
        mReleaseDate = releaseDate
        return this
    }

    fun genreIds(genresId: IntArray): MovieModelBuilder {
        mGenres = genresId
        return this
    }

    fun overview(overview: String): MovieModelBuilder {
        mOverview = overview
        return this
    }
}
