package com.may.tmdb

import android.app.Application
import com.may.tmdb.di.KoinInjector
import com.squareup.leakcanary.LeakCanary
import org.koin.android.ext.android.startKoin


class TMDBApplication : Application() {
    override fun onCreate() {
        super.onCreate()
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