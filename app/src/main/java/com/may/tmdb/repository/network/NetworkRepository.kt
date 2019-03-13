package com.may.tmdb.repository.network

import com.may.tmdb.base.PaginatedResponse
import com.may.tmdb.configuration.ConfigurationModel
import com.may.tmdb.movie.MovieModel
import io.reactivex.Single

interface NetworkRepository {
    fun getConfiguration(): Single<ConfigurationModel>

    fun getUpcomingMovie(page:Int? = null): Single<PaginatedResponse<MovieModel>>
}
