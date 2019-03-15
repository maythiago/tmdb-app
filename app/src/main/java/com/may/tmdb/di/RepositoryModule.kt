package com.may.tmdb.di

import android.content.Context
import android.content.SharedPreferences
import com.may.tmdb.repository.SharedPreferenceRepository
import com.may.tmdb.repository.SharedPreferenceRepositoryImpl
import com.may.tmdb.repository.network.NetworkRepository
import com.may.tmdb.repository.network.NetworkRepositoryImpl
import okhttp3.Cache
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
    single {
        val cacheSize = 10L * 1024L * 1024L // 10mb
        Cache(androidContext().cacheDir, cacheSize)
    }
    single {
        BaseAbstractApplicationModule.provideRetrofitBuilder(get())
    }
    single {
        BaseAbstractApplicationModule.provideRetrofit(get(), get())
    }
    single<NetworkRepository> { NetworkRepositoryImpl(get()) }
}