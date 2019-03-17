package com.may.tmdb.movie.upcoming.paging

import androidx.paging.PageKeyedDataSource
import com.may.tmdb.configuration.ConfigurationModel
import com.may.tmdb.movie.MovieModel
import com.may.tmdb.repository.network.NetworkRepository
import timber.log.Timber

class PageKeyedMovieDataSource(val repository: NetworkRepository, val configuration: ConfigurationModel) :
    PageKeyedDataSource<Int, MovieModel>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MovieModel>) {
        repository
            .getUpcomingMovie(1)
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

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {
        Timber.i("key=${params.key} requestedLoadSize=${params.requestedLoadSize}")
        val currentPage = params.key
        val nextPosition = if (currentPage <= params.requestedLoadSize) {
            currentPage + 1
        } else {
            null
        }

        fetchData(callback, currentPage, currentPage + 1)

    }

    private fun fetchData(
        callback: LoadCallback<Int, MovieModel>, currentPage: Int, nextPosition: Int?
    ) {
        repository
            .getUpcomingMovie(currentPage)
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
        val nextPosition = if (currentPage >= params.requestedLoadSize) {
            currentPage - 1
        } else {
            null
        }
        fetchData(callback, currentPage, currentPage - 1)
    }

}