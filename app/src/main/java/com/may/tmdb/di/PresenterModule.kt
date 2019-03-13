package com.may.tmdb.di

import com.may.tmdb.movie.upcoming.UpcomingMovies
import com.may.tmdb.movie.upcoming.UpcomingMoviesPresenter
import org.koin.dsl.module.module

val presenterModule = module {
    factory<UpcomingMovies.Presenter> { UpcomingMoviesPresenter(get(), get()) }
}