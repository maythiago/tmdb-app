package com.may.tmdb.movie.upcoming.detail

import com.may.tmdb.movie.MovieModel

object MovieDetails {
    interface View {
        fun setPoster(posterPath: String)
        fun setTitle(title: String)
        fun setBackdrop(thumbnailBackdropPath: String)
    }

    interface Presenter {
        fun subscribe(view: MovieDetails.View)
        fun unsubscribe()

        fun onStart(movie: MovieModel)

    }
}
