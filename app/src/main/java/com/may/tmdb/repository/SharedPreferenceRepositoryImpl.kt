package com.may.tmdb.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.may.tmdb.configuration.ConfigurationModel

class SharedPreferenceRepositoryImpl(val sharedPreferences: SharedPreferences, val gson: Gson) :
    SharedPreferenceRepository {
    override fun getConfiguration(): ConfigurationModel? {
        val configuration = sharedPreferences.getString(EXTRA_CONFIGURATION, null)
        return gson.fromJson(configuration, ConfigurationModel::class.java)
    }

    override fun setConfiguration(configuration: ConfigurationModel) {
        val json = gson.toJson(configuration)
        sharedPreferences
            .edit()
            .putString(EXTRA_CONFIGURATION, json)
            .apply()
    }

    companion object {
        const val EXTRA_CONFIGURATION = "EXTRA_CONFIGURATION"
        const val SHARED_PREFERENCE_NAME = "TMDB_SHARED_PREFERENCES_NAME"
    }
}