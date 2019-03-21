package com.may.tmdb.di

import org.koin.dsl.module.Module

object KoinInjector {
    fun modules(): List<Module> {
        return listOf(
            gsonModule,
            networkRepositoryModule,
            sharedPreferenceRepository,
            presenterModule
        )
    }
}