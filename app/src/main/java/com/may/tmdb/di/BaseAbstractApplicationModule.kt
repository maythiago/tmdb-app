package com.may.tmdb.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.may.tmdb.BuildConfig
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object BaseAbstractApplicationModule {
    private var mRetrofit: Retrofit? = null

    fun providesGsonBuilder(): GsonBuilder {
        return GsonBuilder()
    }

    fun provideRetrofitBuilder(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.newThread()))
    }

    fun provideRetrofit(retrofitBuilder: Retrofit.Builder): IRetrofitFactory {
        val builder = OkHttpClient.Builder()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)

        val client = builder.build()
        return object : IRetrofitFactory {
            override fun get(): Retrofit {
                if (mRetrofit == null || mRetrofit!!.baseUrl().toString() != BuildConfig.TMDB_API_BASE_URL) {
                    mRetrofit = retrofitBuilder
                        .baseUrl(BuildConfig.TMDB_API_BASE_URL)
                        .client(client)
                        .build()
                }
                return mRetrofit!!
            }
        }
    }
}