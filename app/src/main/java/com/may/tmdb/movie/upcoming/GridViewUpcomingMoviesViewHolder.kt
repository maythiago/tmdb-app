package com.may.tmdb.movie.upcoming

import android.os.Build
import android.view.View
import com.may.tmdb.base.BindableViewHolder
import com.may.tmdb.movie.MovieModel
import kotlinx.android.synthetic.main.view_holder_upcoming_movies_grid_view.view.*

class GridViewUpcomingMoviesViewHolder(
    itemView: View,
    viewHolderClickListener: (Int) -> Unit
) : BindableViewHolder<MovieModel>(itemView) {
    val poster = itemView.ivUpcomingMoviesPoster
    val title = itemView.ivGridViewUpcomingMoviesPosterTitle
    init {
        itemView.setOnClickListener {
            viewHolderClickListener.invoke(adapterPosition)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            poster.transitionName = "moviePoster"
        }
    }

    override fun bind(item: MovieModel) {
        title.text = item.title
        PosterProvider.load(itemView.context, item.thumbnailPosterPath, poster)
    }

    override fun clear() {

    }
}

