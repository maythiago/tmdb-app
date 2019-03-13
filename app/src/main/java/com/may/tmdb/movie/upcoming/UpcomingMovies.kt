package com.may.tmdb.movie.upcoming

import com.may.tmdb.movie.MovieModel

object UpcomingMovies {
    interface View {
        fun showConfigurationError(message: String)
        fun showMovies(results: Array<MovieModel>)
        fun removeAllMovies()
        fun showNotFoundServiceError()
    }

    interface Presenter {
        fun subscribe(view: UpcomingMovies.View)
        fun unsubscribe()
        fun onStart()
    }

}