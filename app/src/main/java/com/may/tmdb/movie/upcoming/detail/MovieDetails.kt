package com.may.tmdb.movie.upcoming.detail

import com.may.tmdb.movie.MovieModel

object MovieDetails {
    interface View {
        fun setPoster(posterPath: String?)

    }

    interface Presenter {
        fun subscribe(view: MovieDetails.View)
        fun unsubscribe()

        fun onStart(movie: MovieModel)

    }
}
