package com.may.tmdb.repository

import com.may.tmdb.configuration.ConfigurationModel
import io.reactivex.Single

class NetworkRepositoryImpl : NetworkRepository {
    override fun getConfiguration(): Single<ConfigurationModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}