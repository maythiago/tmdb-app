package com.may.tmdb.base

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.may.tmdb.BuildConfig
import com.may.tmdb.R
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
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

    fun provideRetrofit(retrofitBuilder: Retrofit.Builder, cache: Cache, context: Context): Retrofit {
        val builder = OkHttpClient.Builder()
            .cache(cache)
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }
        builder.addInterceptor { chain ->
            var request = chain.request()
            val oldUrl = request
                .url()
            val language = "${Locale.getDefault().language}-${Locale.getDefault().country}"
            val apiKey = context.getString(R.string.tmdb_api_key)
            val newUrl = oldUrl
                .newBuilder()
                .addQueryParameter(HEADER_API_KEY, apiKey)
                .addQueryParameter(HEADER_LANGUAGE, language)
                .addQueryParameter(HEADER_REGION, Locale.getDefault().country)
                .build()
            request = request.newBuilder().url(newUrl).build()
            chain.proceed(request)

        }
        val client = builder.build()
        val baseUrl = context.getString(R.string.tmdb_api_base_url)
        if (mRetrofit == null || mRetrofit!!.baseUrl().toString() != baseUrl) {
            mRetrofit = retrofitBuilder
                .baseUrl(baseUrl)
                .client(client)
                .build()
        }
        return mRetrofit!!
    }

    const val HEADER_API_KEY = "api_key"
    const val HEADER_LANGUAGE = "language"
    const val HEADER_REGION = "region"

}
