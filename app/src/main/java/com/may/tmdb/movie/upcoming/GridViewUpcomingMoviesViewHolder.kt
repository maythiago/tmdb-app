package com.may.tmdb.movie.upcoming

import android.view.View
import com.may.tmdb.base.BindableViewHolder
import com.may.tmdb.movie.MovieModel
import kotlinx.android.synthetic.main.view_holder_upcoming_movies_grid_view.view.*

class GridViewUpcomingMoviesViewHolder(
    itemView: View,
    viewHolderClickListener: (Int) -> Unit
) : BindableViewHolder<MovieModel>(itemView) {
    init {
        itemView.setOnClickListener {
            viewHolderClickListener.invoke(adapterPosition)
        }
    }

    val poster = itemView.ivGridViewUpcomingMoviesPoster
    val title = itemView.ivGridViewUpcomingMoviesPosterTitle

    override fun bind(item: MovieModel) {
        title.text = item.title
        PosterProvider.load(itemView.context, item.posterPath, poster)
    }

    override fun clear() {

    }
}

