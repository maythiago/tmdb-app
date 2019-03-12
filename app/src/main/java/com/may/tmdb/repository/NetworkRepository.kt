package com.may.tmdb.repository

import com.may.tmdb.configuration.ConfigurationModel
import io.reactivex.Single

interface NetworkRepository {
    fun getConfiguration(): Single<ConfigurationModel>

}
