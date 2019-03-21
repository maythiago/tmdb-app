package com.may.tmdb.movie.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.may.tmdb.FullScreenActivity
import com.may.tmdb.R
import com.may.tmdb.movie.MovieModel

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val movie = intent.extras.getParcelable<MovieModel>(EXTRA_MOVIE_DETAIL)
            goToFragment(MovieDetailsFragment.newInstance(movie))
        }
    }

    fun goToFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flCurrentFragment, fragment)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFinishAfterTransition()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    fun openFullScreenImage(posterUrl: String) {
        val fullScreenActivity = FullScreenActivity.startIntent(this, posterUrl)
        startActivity(fullScreenActivity)
    }

    companion object {
        fun startIntent(context: Context, movieModel: MovieModel): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_DETAIL, movieModel)
            }
        }

        const val EXTRA_MOVIE_DETAIL = "EXTRA_MOVIE_DETAIL"
    }
}