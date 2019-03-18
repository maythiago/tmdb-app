package com.may.tmdb.repository.network

import com.may.tmdb.base.PaginatedResponse
import com.may.tmdb.configuration.ConfigurationModel
import com.may.tmdb.movie.MovieModel
import com.may.tmdb.movie.genre.GenreModel
import com.may.tmdb.movie.genre.GenreResponseModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("configuration")
    fun getConfiguration(): Single<ConfigurationModel>

    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("page") page: Int?): Single<PaginatedResponse<MovieModel>>

    @GET("genre/movie/list")
    fun getGenres(): Single<GenreResponseModel>
}