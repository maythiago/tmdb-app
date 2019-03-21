package com.may.tmdb.movie.detail

import com.may.tmdb.movie.MovieModel
import com.may.tmdb.repository.network.NetworkRepository
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import com.may.tmdb.extensions.CompositeDisposableExtension.plusAssign

class
MovieDetailsPresenter(val mNetworkRepositoryImpl: NetworkRepository) :
    MovieDetails.Presenter {
    override fun onPosterClicked(movie: MovieModel) {
        val posterUrl = movie.largePosterPath
        if (!posterUrl.isNullOrBlank()) {
            mView?.openPosterFullScreen(posterUrl)
        }
    }

    var mView: MovieDetails.View? = null
    val compositeDisposable = CompositeDisposable()
    override fun onStart(movie: MovieModel) {
        if (!movie.thumbnailPosterPath.isNullOrBlank()) {
            mView?.setPoster(movie.thumbnailPosterPath!!)
        }
        val overview = movie.overview
        if (overview.isEmpty()) {
            mView?.showEmptyOverview()
        } else {
            mView?.setOverview(overview)
        }
        mView?.setTitle(movie.title)
        mView?.setReleaseDate(movie.releaseDate)
        Timber.i("i will call getGenres")
        compositeDisposable += mNetworkRepositoryImpl
            .getGenres()
            .doOnTerminate { mView?.hideProgress() }
            .doOnSubscribe { mView?.showProgress() }
            .subscribe(
                { genresResponse ->
                    Timber.i("i receive $genresResponse in getGenres")
                    val genres = movie.genresId.map { x ->
                        genresResponse.genres.first { y -> x == y.id }
                    }
                    Timber.i("This movie has $genres genres")
                    val readableGenre = genres.joinToString { it.name }
                    Timber.i("And that is how i read this genres $readableGenre")
                    mView?.apply {
                        Timber.i("Aplying in the view")
                        setGenre(readableGenre)
                        Timber.i("My job is done :)")
                    }
                },
                { error ->
                    Timber.i("Something strange happened $error")

                })
    }

    override fun subscribe(view: MovieDetails.View) {
        mView = view
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }

}