package com.may.tmdb.mock

import androidx.paging.DataSource
import com.may.tmdb.movie.MovieModel

class MockDataSourceFactory(val item: MutableList<MovieModel>) : DataSource.Factory<Int, MovieModel>() {
    override fun create(): DataSource<Int, MovieModel> {
        val source = PageKeyedMockDataSource(item)
        return source
    }
}

