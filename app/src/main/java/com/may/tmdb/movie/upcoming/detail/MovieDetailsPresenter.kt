package com.may.tmdb.movie.upcoming.detail

import com.may.tmdb.movie.MovieModel
import com.may.tmdb.movie.upcoming.detail.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class
MovieDetailsPresenter : MovieDetails.Presenter {
    var mView: MovieDetails.View? = null
    val compositeDisposable = CompositeDisposable()
    override fun onStart(movie: MovieModel) {
        mView?.setPoster(movie.largePosterPath)
    }

    override fun subscribe(view: MovieDetails.View) {
        mView = view
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }

}