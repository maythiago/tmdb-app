package com.may.tmdb.movie.upcoming

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.may.tmdb.R
import com.may.tmdb.base.BindableViewHolder
import com.may.tmdb.movie.MovieModel
import kotlinx.android.synthetic.main.view_holder_upcoming_movies_single_line.view.*

class SingleLineUpcomingMoviesViewHolder(
    itemView: View,
    viewHolderClickListener: (Int) -> Unit
) : BindableViewHolder<MovieModel>(itemView) {
    init {
        itemView.setOnClickListener {
            viewHolderClickListener.invoke(adapterPosition)
        }
    }

    val poster = itemView.ivUpcomingMoviesPoster
    val title = itemView.ivUpcomingMoviesPosterTitle
    val overview = itemView.ivUpcomingMoviesPosterOverview

    override fun bind(item: MovieModel) {
        title.text = item.title
        overview.text = item.overview
        PosterProvider.load(itemView.context, item.posterPath, poster)
    }

    override fun clear() {

    }
}

