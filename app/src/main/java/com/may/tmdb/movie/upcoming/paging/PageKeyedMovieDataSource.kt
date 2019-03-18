package com.may.tmdb.movie.upcoming.paging

import androidx.paging.PageKeyedDataSource
import com.may.tmdb.base.PaginatedResponse
import com.may.tmdb.configuration.ConfigurationModel
import com.may.tmdb.movie.MovieModel
import com.may.tmdb.repository.network.NetworkRepository
import io.reactivex.Single
import timber.log.Timber

class PageKeyedMovieDataSource(
    val repository: NetworkRepository,
    val configuration: ConfigurationModel
) :
    PageKeyedDataSource<Int, MovieModel>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MovieModel>) {
        getUpComingMovie(1)
            .subscribe(
                { response ->
                    var movies = response
                        .results
                        .map {
                            it.withConfiguration(configuration!!.images)
                        }
                    callback.onResult(movies, null, 2)
                },
                { e -> })

    }

    private fun getUpComingMovie(page: Int): Single<PaginatedResponse<MovieModel>> {
        return repository
            .getUpcomingMovie(page)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {
        Timber.i("key=${params.key} requestedLoadSize=${params.requestedLoadSize}")
        val currentPage = params.key
        fetchData(callback, currentPage, currentPage + 1)

    }

    private fun fetchData(
        callback: LoadCallback<Int, MovieModel>, currentPage: Int, nextPosition: Int?
    ) {
        getUpComingMovie(currentPage)
            .subscribe(
                { response ->

                    var movies = response
                        .results
                        .map {
                            it.withConfiguration(configuration!!.images)
                        }
                    callback.onResult(movies, nextPosition)
                },
                { e -> })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {
        Timber.i("key=${params.key} requestedLoadSize=${params.requestedLoadSize}")
        val currentPage = params.key
        fetchData(callback, currentPage, currentPage - 1)
    }

}