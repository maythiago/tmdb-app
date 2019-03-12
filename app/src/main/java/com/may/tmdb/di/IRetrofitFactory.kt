package com.may.tmdb.di

import retrofit2.Retrofit

interface IRetrofitFactory {
    fun get(): Retrofit
}
