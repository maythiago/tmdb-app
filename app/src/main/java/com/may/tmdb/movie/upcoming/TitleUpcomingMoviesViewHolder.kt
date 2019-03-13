package com.may.tmdb.movie.upcoming

import android.view.View
import com.may.tmdb.base.BindableViewHolder
import com.may.tmdb.movie.MovieModel
import kotlinx.android.synthetic.main.view_holder_upcoming_movies_title.view.*

class TitleUpcomingMoviesViewHolder(itemView: View) : BindableViewHolder<MovieModel>(itemView) {
    val title = itemView.tvUpcomingMoviesTitle

    override fun bind(movie: MovieModel) {
        title.text = movie.title

    }
}

