package com.may.tmdb.di

import com.may.tmdb.upcoming.UpcomingMovies
import com.may.tmdb.upcoming.UpcomingMoviesPresenter
import org.koin.dsl.module.module

val presenterModule = module {
    single<UpcomingMovies.Presenter> { UpcomingMoviesPresenter(get(), get()) }

}