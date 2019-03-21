package com.may.tmdb.movie.upcoming

import androidx.paging.PagedList
import com.may.tmdb.movie.MovieModel

object UpcomingMovies {
    interface View {
        fun showConfigurationError()
        fun showMovies(results: PagedList<MovieModel>)
        fun showEmptyState()
        fun openMovieDetails(position: Int, movie: MovieModel)
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter {
        fun subscribe(view: UpcomingMovies.View)
        fun unsubscribe()
        fun onStart()
        fun handleMovieClicked(position: Int, movie: MovieModel)
        fun onRefreshListener()
    }

}