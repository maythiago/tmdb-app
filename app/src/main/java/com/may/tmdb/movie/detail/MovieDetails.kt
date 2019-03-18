package com.may.tmdb.movie.detail

import com.may.tmdb.movie.MovieModel

object MovieDetails {
    interface View {
        fun setPoster(posterPath: String)
        fun setTitle(title: String)
        fun setGenre(genre: String)
        fun showProgress()
        fun hideProgress()
        fun setReleaseDate(releaseDate: String)
        fun setOverview(overview: String)
        fun showEmptyOverview()

    }

    interface Presenter {
        fun subscribe(view: View)
        fun unsubscribe()
        fun onStart(movie: MovieModel)

    }
}
