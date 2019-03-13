package com.may.tmdb

import android.app.Application
import com.may.tmdb.di.KoinInjector
import com.squareup.leakcanary.LeakCanary
import org.koin.android.ext.android.startKoin
import timber.log.Timber


class TMDBApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        initKoinModules()
        initLeakCanary()
    }

    private fun initKoinModules() {
        startKoin(this, KoinInjector.modules())
    }

    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }
}