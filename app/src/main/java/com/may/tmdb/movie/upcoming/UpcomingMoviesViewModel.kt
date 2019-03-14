package com.may.tmdb.movie.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.may.tmdb.MovieModelDataSourceFactory
import com.may.tmdb.configuration.ConfigurationModel
import com.may.tmdb.extensions.CompositeDisposableExtension.plusAssign
import com.may.tmdb.movie.MovieModel
import com.may.tmdb.repository.network.NetworkRepository
import com.may.tmdb.repository.SharedPreferenceRepository
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException

class UpcomingMoviesViewModel(
    val mSharedPreferenceRepository: SharedPreferenceRepository,
    val mNetworkRepository: NetworkRepository
) : ViewModel() {
    val error = MutableLiveData<String>()
    private val mCompositeDisposable = CompositeDisposable()
    val dataSourceFactory by lazy { MovieModelDataSourceFactory(mNetworkRepository, configuration.value!!) }
    val movies by lazy { LivePagedListBuilder(dataSourceFactory, 14).build() }
    val configuration = MutableLiveData<ConfigurationModel>()

    fun onStart() {
        val cachedConfiguration = mSharedPreferenceRepository.getConfiguration()
        if (cachedConfiguration == null) {
            mCompositeDisposable += mNetworkRepository
                .getConfiguration()
                .subscribe({ configuration ->
                    mSharedPreferenceRepository.setConfiguration(configuration)
                    getUpcomingMovies(configuration)
                }, { e ->
                    error.postValue(e.message ?: "Ocorreu um erro inesperado")
                })
        } else {
            getUpcomingMovies(cachedConfiguration)
        }
    }

    private fun getUpcomingMovies(configurationModel: ConfigurationModel) {
        configuration.postValue(configurationModel)
    }

    override fun onCleared() {
        mCompositeDisposable.clear()
        super.onCleared()
    }
}

