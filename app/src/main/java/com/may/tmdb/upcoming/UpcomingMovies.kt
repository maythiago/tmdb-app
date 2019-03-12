package com.may.tmdb.upcoming

object UpcomingMovies {
    interface View {
        fun showConfigurationError(message: String)

    }

    interface Presenter {
        fun subscribe(view: UpcomingMovies.View)
        fun unsubscribe()
        fun onStart()
    }

}