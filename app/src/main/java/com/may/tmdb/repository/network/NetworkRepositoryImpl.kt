package com.may.tmdb.repository.network

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.may.tmdb.movie.upcoming.paging.MovieModelDataSourceFactory
import com.may.tmdb.base.PaginatedResponse
import com.may.tmdb.configuration.ConfigurationModel
import com.may.tmdb.movie.MovieModel
import com.may.tmdb.movie.genre.GenreResponseModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.Cache
import retrofit2.Retrofit

class NetworkRepositoryImpl(val repository: Retrofit, val cache: Cache) : NetworkRepository {
    val mApi = repository.create(API::class.java)
    lateinit var dataSourceFactory: MovieModelDataSourceFactory
    override fun getGenres(): Single<GenreResponseModel> {
        return mApi
            .getGenres()
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getConfiguration(): Single<ConfigurationModel> {
        return mApi
            .getConfiguration()
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getUpcomingMovie(page: Int?): Single<PaginatedResponse<MovieModel>> {
        return mApi
            .getUpcomingMovies(page)
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getPagingUpcomingMovie(configurationModel: ConfigurationModel): Observable<PagedList<MovieModel>> {
        dataSourceFactory = MovieModelDataSourceFactory(this, configurationModel)
        return RxPagedListBuilder(dataSourceFactory, MAX_PAGE)
            .buildObservable()
    }

    override fun invalidateData() {
        cache.evictAll()
        if (::dataSourceFactory.isInitialized) {
            dataSourceFactory.invalidate()
        }
    }

    companion object {
        const val MAX_PAGE = 20
    }
}