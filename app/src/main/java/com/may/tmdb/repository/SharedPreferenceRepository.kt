package com.may.tmdb.repository

import com.may.tmdb.configuration.ConfigurationModel

interface SharedPreferenceRepository {
    fun getConfiguration(): ConfigurationModel?
    fun setConfiguration(configuration: ConfigurationModel)

}
