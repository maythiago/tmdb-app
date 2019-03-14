package com.may.tmdb.movie.upcoming

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.may.tmdb.R
import com.may.tmdb.base.BindableViewHolder
import com.may.tmdb.movie.MovieModel
import kotlinx.android.synthetic.main.view_holder_upcoming_movies_single_line.view.*

class SingleLineUpcomingMoviesViewHolder(itemView: View) : BindableViewHolder<MovieModel>(itemView) {
    val poster = itemView.ivUpcomingMoviesPoster
    val title = itemView.ivUpcomingMoviesPosterTitle
    val overview = itemView.ivUpcomingMoviesPosterOverview

    override fun bind(item: MovieModel) {
        title.text = item.title
        overview.text = item.overview
        Glide.with(itemView.context)
            .load(item.posterPath)
            .error(R.drawable.poster_placeholder)
            .placeholder(R.drawable.poster_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(poster)
    }

    override fun clear() {

    }
}

