package com.may.tmdb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.may.tmdb.upcoming.UpcomingMoviesFragment

class TMDBActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.flCurrentFragment, UpcomingMoviesFragment.newInstance())
        }
    }
}
