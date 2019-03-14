package com.may.tmdb.di

import com.may.tmdb.movie.upcoming.UpcomingMoviesViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val presenterModule = module {
    viewModel {UpcomingMoviesViewModel(get(), get())}
//    factory<UpcomingMovies.Presenter> { UpcomingMoviesPresenter(get(), get()) }
}