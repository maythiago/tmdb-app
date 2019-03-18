package com.may.tmdb.base

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.may.tmdb.BuildConfig
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

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

    fun provideRetrofit(retrofitBuilder: Retrofit.Builder, cache: Cache): Retrofit {
        val builder = OkHttpClient.Builder()
            .cache(cache)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
        builder.addInterceptor { chain ->
            var request = chain.request()
            val oldUrl = request
                .url()
            val newUrl = oldUrl
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                .addQueryParameter("language", "${Locale.getDefault().language}-${Locale.getDefault().country}")
                .addQueryParameter("region", Locale.getDefault().country)
                .build()
            request = request.newBuilder().url(newUrl).build()
            chain.proceed(request)

        }
        val client = builder.build()
        if (mRetrofit == null || mRetrofit!!.baseUrl().toString() != BuildConfig.TMDB_API_BASE_URL) {
            mRetrofit = retrofitBuilder
                .baseUrl(BuildConfig.TMDB_API_BASE_URL)
                .client(client)
                .build()
        }
        return mRetrofit!!
    }
}
