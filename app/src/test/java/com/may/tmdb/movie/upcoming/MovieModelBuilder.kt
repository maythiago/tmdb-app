package com.may.tmdb.movie.upcoming

import com.may.tmdb.movie.MovieModel

class MovieModelBuilder {
    private var posterPath: String? = null
    private var largePosterPath: String? = null
    private var mThumbnailPosterPath: String? = null
    private val releaseDate = ""
    private val id = System.currentTimeMillis().toInt()
    private var mTitle = ""
    private val mBackdropPath: String? = null

    private var mLargeBackdropPath: String? = null

    fun build(): MovieModel {
        return MovieModel(
            posterPath, releaseDate, id, mTitle, mBackdropPath, largePosterPath,
            mThumbnailPosterPath, mLargeBackdropPath
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

    fun backdropUrl(backdrop: String): MovieModelBuilder {
        mLargeBackdropPath = backdrop
        return this
    }

}
