package com.may.tmdb.movie.upcoming

import com.may.tmdb.extensions.CompositeDisposableExtension.plusAssign
import com.may.tmdb.movie.MovieModel
import com.may.tmdb.repository.network.NetworkRepository
import com.may.tmdb.repository.SharedPreferenceRepository
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import timber.log.Timber

class UpcomingMoviesPresenter(
    val mSharedPreferenceRepository: SharedPreferenceRepository,
    val mNetworkRepository: NetworkRepository
) : UpcomingMovies.Presenter {
    private var mView: UpcomingMovies.View? = null
    private val mCompositeDisposable = CompositeDisposable()

    override fun onStart() {
        val cachedConfiguration = mSharedPreferenceRepository.getConfiguration()
        if (cachedConfiguration == null) {
            mCompositeDisposable += mNetworkRepository
                .getConfiguration()
                .doOnSubscribe { mView?.showProgress() }
                .doOnTerminate { mView?.hideProgress() }
                .subscribe({ configuration ->
                    mView?.showEmptyState()
                    mSharedPreferenceRepository.setConfiguration(configuration)
                    getUpcomingMovies()
                }, { e ->
                    mView?.showConfigurationError()
                    mView?.showEmptyState()
                })
        } else {
            getUpcomingMovies()
        }
    }

    private fun getUpcomingMovies() {
        val configuration = mSharedPreferenceRepository.getConfiguration()
        if (configuration != null) {
            mCompositeDisposable += mNetworkRepository.getPagingUpcomingMovie(configuration!!)
                .subscribe({ result ->
                    Timber.i("showMovies=$result")
                    mView?.showMovies(result)
                }, { e ->
                    mView?.showConfigurationError()
                })
        }
    }

    override fun handleMovieClicked(position: Int, movie: MovieModel) {
        mView?.openMovieDetails(position, movie)
    }

    override fun onRefreshListener() {
//        mView?.showEmptyState()
        mNetworkRepository.invalidateData()
    }

    override fun subscribe(view: UpcomingMovies.View) {
        mView = view
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
        mView = null
    }
}

