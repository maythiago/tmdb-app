package com.may.tmdb.movie.upcoming

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.may.tmdb.base.BindableViewHolder
import com.may.tmdb.movie.MovieModel
import kotlinx.android.synthetic.main.view_holder_upcoming_movies_poster.view.*

class PosterUpcomingMoviesViewHolder(itemView: View) : BindableViewHolder<MovieModel>(itemView) {
    val poster = itemView.ivUpcomingMoviesPoster

    override fun bind(item: MovieModel) {
        Glide.with(itemView.context)
            .load(item.posterPath)
            .error(ColorDrawable(Color.RED))
            .placeholder(ColorDrawable(Color.LTGRAY))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .fitCenter()
            .into(poster)
    }
}

