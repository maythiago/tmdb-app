package com.may.tmdb

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.may.tmdb.movie.MovieModel
import com.may.tmdb.movie.upcoming.UpcomingMoviesFragment
import androidx.core.app.ActivityOptionsCompat
import com.may.tmdb.movie.upcoming.detail.DetailActivity


class UpcomingMovieActivity : AppCompatActivity() {

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
            .commit()
    }

    fun goToDetailsFragment(movie: MovieModel, viewClicked:View) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            viewClicked,
            "moviePoster"
        )
        startActivity(DetailActivity.startIntent(this, movie), options.toBundle())
    }
}

