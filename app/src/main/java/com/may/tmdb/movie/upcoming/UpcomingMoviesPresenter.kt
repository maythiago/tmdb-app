package com.may.tmdb.movie.upcoming

import com.may.tmdb.MovieModelDataSourceFactory
import com.may.tmdb.extensions.CompositeDisposableExtension.plusAssign
import com.may.tmdb.repository.network.NetworkRepository
import com.may.tmdb.repository.SharedPreferenceRepository
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException

class UpcomingMoviesPresenter(
    val mSharedPreferenceRepository: SharedPreferenceRepository,
    val mNetworkRepository: NetworkRepository
) : UpcomingMovies.Presenter {
    private val mCompositeDisposable = CompositeDisposable()
    override fun onStart() {
        val cachedConfiguration = mSharedPreferenceRepository.getConfiguration()
        if (cachedConfiguration == null) {
            mCompositeDisposable += mNetworkRepository
                .getConfiguration()
                .subscribe({ configuration ->
                    mSharedPreferenceRepository.setConfiguration(configuration)
                    getUpcomingMovies()
                }, { e ->
                    mView?.showConfigurationError(e.message ?: "Ocorreu um erro inesperado")
                })
        } else {
            getUpcomingMovies()
        }
    }

    private fun getUpcomingMovies() {
        mCompositeDisposable += mNetworkRepository
            .getUpcomingMovie()
            .subscribe({ result ->
                val configuration = mSharedPreferenceRepository.getConfiguration()
                val moviesWithConfiguration = result
                    .results
                    .map {
                        it.withBaseImageUrl(
                            configuration!!
                                .images
                                .baseUrl
                        )
                    }.toTypedArray()
                mView?.removeAllMovies()
                mView?.showMovies(moviesWithConfiguration)
            }, { e ->
                if (e is HttpException && e.code() == 404) {
                    mView?.showNotFoundServiceError()
                } else {
                    mView?.showConfigurationError(e.message ?: "Ocorreu um erro inesperado")
                }
            })
    }

    private var mView: UpcomingMovies.View? = null
    override fun subscribe(view: UpcomingMovies.View) {
        mView = view
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
        mView = null
    }
}

