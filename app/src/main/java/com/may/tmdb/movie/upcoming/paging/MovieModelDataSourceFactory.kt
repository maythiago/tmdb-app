package com.may.tmdb.movie.upcoming.paging

import androidx.paging.DataSource
import com.may.tmdb.configuration.ConfigurationModel
import com.may.tmdb.movie.MovieModel
import com.may.tmdb.repository.network.NetworkRepository

class MovieModelDataSourceFactory(
    val repository: NetworkRepository, val configuration: ConfigurationModel
) : DataSource.Factory<Int, MovieModel>() {

    override fun create(): DataSource<Int, MovieModel> {
        val source = PageKeyedMovieDataSource(repository, configuration)
        return source
    }
}

