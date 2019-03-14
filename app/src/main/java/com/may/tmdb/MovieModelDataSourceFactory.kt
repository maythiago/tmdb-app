package com.may.tmdb

import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PositionalDataSource
import com.may.tmdb.configuration.ConfigurationModel
import com.may.tmdb.di.networkRepositoryModule
import com.may.tmdb.movie.MovieModel
import com.may.tmdb.repository.SharedPreferenceRepository
import com.may.tmdb.repository.network.NetworkRepository
import timber.log.Timber

class MovieModelDataSourceFactory(val repository: NetworkRepository, val configuration: ConfigurationModel) :
    DataSource.Factory<Int, MovieModel>() {
    override fun create(): DataSource<Int, MovieModel> {
        val source = PageKeyedMovieDataSource(repository, configuration)
        return source
    }


}

class PageKeyedMovieDataSource(val repository: NetworkRepository, val configuration: ConfigurationModel) :
    PageKeyedDataSource<Int, MovieModel>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MovieModel>) {
        repository
            .getUpcomingMovie()
            .subscribe(
                { response ->
                    var movies = response
                        .results
                        .map {
                            it.withBaseImageUrl(
                                configuration!!
                                    .images
                                    .baseUrl
                            )
                        }
                    callback.onResult(movies, null, 2)
                },
                { e -> })

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {
        Timber.i("key=${params.key} requestedLoadSize=${params.requestedLoadSize}")
        val currentPage = params.key
        val nextPosition = if (currentPage < params.requestedLoadSize) {
            currentPage + 1
        } else {
            null
        }
        fetchData(callback, nextPosition)

    }

    private fun fetchData(
        callback: LoadCallback<Int, MovieModel>,
        nextPosition: Int?
    ) {
        repository
            .getUpcomingMovie(nextPosition)
            .subscribe(
                { response ->

                    var movies = response
                        .results
                        .map {
                            it.withBaseImageUrl(
                                configuration!!
                                    .images
                                    .baseUrl
                            )
                        }
                    callback.onResult(movies, nextPosition)
                },
                { e -> })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {
        Timber.i("key=${params.key} requestedLoadSize=${params.requestedLoadSize}")
        val currentPage = params.key
        val nextPosition = if (currentPage > params.requestedLoadSize) {
            currentPage - 1
        } else {
            null
        }
        fetchData(callback, nextPosition)
    }

}
