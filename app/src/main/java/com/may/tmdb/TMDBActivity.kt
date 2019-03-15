package com.may.tmdb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.may.tmdb.movie.upcoming.UpcomingMoviesFragment

class TMDBActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            goToFragment(UpcomingMoviesFragment.newInstance())
        }
    }

    fun goToFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flCurrentFragment, fragment)
            .commit()    }
}
