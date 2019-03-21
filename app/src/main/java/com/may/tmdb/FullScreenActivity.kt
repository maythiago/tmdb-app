package com.may.tmdb

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.full_screen_activity.*


class FullScreenActivity : AppCompatActivity() {
    val posterUrl by lazy {
        intent.extras.getString(EXTRA_MOVIE_POSTER_URL)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.full_screen_activity);
        Glide.with(this)
            .load(posterUrl)
            .placeholder(R.drawable.poster_placeholder)
            .error(R.drawable.poster_placeholder)
            .into(ivFullScreen);
    }

    companion object {
        fun startIntent(context: Context, posterUrl: String): Intent {
            return Intent(context, FullScreenActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_POSTER_URL, posterUrl)
            }
        }

        const val EXTRA_MOVIE_POSTER_URL = "EXTRA_MOVIE_POSTER_URL"
    }
}

