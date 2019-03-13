package com.may.tmdb.repository.network

import com.google.gson.Gson
import com.may.tmdb.base.PaginatedResponse
import com.may.tmdb.configuration.ConfigurationModel
import com.may.tmdb.movie.MovieModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.Retrofit

class NetworkRepositoryImpl(retrofitFactory: Retrofit, gson: Gson) : NetworkRepository {
    private val mApi = retrofitFactory.create(API::class.java)

    override fun getConfiguration(): Single<ConfigurationModel> {
        return mApi
            .getConfiguration()
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getUpcomingMovie(page: Int?): Single<PaginatedResponse<MovieModel>> {
        return mApi
            .getUpcomingMovies(page)
            .observeOn(AndroidSchedulers.mainThread())
    }
}