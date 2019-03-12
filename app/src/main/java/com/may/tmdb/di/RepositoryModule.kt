package com.may.tmdb.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.may.tmdb.repository.SharedPreferenceRepository
import com.may.tmdb.repository.SharedPreferenceRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val sharedPreferenceRepository = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            SharedPreferenceRepositoryImpl.SHARED_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )
    }
    single<SharedPreferenceRepository> { SharedPreferenceRepositoryImpl(get(), get()) }
}

val networkRepositoryModule = module {
    factory {
        BaseAbstractApplicationModule.providesGsonBuilder()
    }
    single<Gson> {
        get<GsonBuilder>().create()
    }
    single {
        BaseAbstractApplicationModule.provideRetrofitBuilder(get())
    }
    single {
        single { BaseAbstractApplicationModule.provideRetrofit(get()) }
    }
}