package com.may.tmdb.mock

import androidx.paging.PageKeyedDataSource
import com.may.tmdb.movie.MovieModel


class PageKeyedMockDataSource(val item: MutableList<MovieModel>) :
    PageKeyedDataSource<Int, MovieModel>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MovieModel>) {

        println("loadInitial=$item")
        callback.onResult(item, null, 2)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {
        println("loadAfter=$item")
        fetchData(callback)

    }

    private fun fetchData(callback: LoadCallback<Int, MovieModel>) {
        println("fetchData=$item")
        callback.onResult(item, null)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {
        println("loadBefore=$item")
        fetchData(callback)
    }

}