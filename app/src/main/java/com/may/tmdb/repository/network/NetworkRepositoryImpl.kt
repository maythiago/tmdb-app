package com.may.tmdb.repository.network

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.may.tmdb.movie.upcoming.paging.MovieModelDataSourceFactory
import com.may.tmdb.base.PaginatedResponse
import com.may.tmdb.configuration.ConfigurationModel
import com.may.tmdb.movie.MovieModel
import com.may.tmdb.movie.genre.GenreModel
import com.may.tmdb.movie.genre.GenreResponseModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.Retrofit

class NetworkRepositoryImpl(repository: Retrofit) : NetworkRepository {
    val mApi = repository.create(API::class.java)
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

    override fun getPagingUpcomingMovie(
        configurationModel: ConfigurationModel
    ): Observable<PagedList<MovieModel>> {
        val movieModelDataSourceFactory = MovieModelDataSourceFactory(this, configurationModel)
        return RxPagedListBuilder(movieModelDataSourceFactory, MAX_PAGE)
            .buildObservable()
    }

    companion object {
        const val MAX_PAGE = 20

    }

}