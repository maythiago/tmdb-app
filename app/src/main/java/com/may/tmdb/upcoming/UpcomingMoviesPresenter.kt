package com.may.tmdb.upcoming

import com.may.tmdb.extension.CompositeDisposableExtension.plusAssign
import com.may.tmdb.repository.NetworkRepository
import com.may.tmdb.repository.SharedPreferenceRepository
import io.reactivex.disposables.CompositeDisposable

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
                }, { e ->
                    mView?.showConfigurationError(e.message ?: "Ocorreu um erro inesperado")
                })
        }
    }

    private var mView: UpcomingMovies.View? = null
    override fun subscribe(view: UpcomingMovies.View) {
        mView = view
    }

    override fun unsubscribe() {
        mView = null
    }
}

