package com.may.tmdb.movie.upcoming

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.may.tmdb.R

object PosterProvider {
    fun load(context: Context, url: String?, loadView: ImageView) {
        Glide.with(context)
            .load(url)
            .error(R.drawable.poster_placeholder)
            .placeholder(R.drawable.poster_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(loadView)
    }
}