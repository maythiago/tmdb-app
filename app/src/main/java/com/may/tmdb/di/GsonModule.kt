package com.may.tmdb.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.dsl.module.module

val gsonModule = module {
    factory {
        BaseAbstractApplicationModule.providesGsonBuilder()
    }
    single<Gson> {
        get<GsonBuilder>().create()
    }
}