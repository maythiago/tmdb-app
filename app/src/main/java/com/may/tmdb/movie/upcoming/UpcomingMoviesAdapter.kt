package com.may.tmdb.movie.upcoming

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.may.tmdb.R
import com.may.tmdb.base.BindableViewHolder
import com.may.tmdb.movie.MovieModel

class UpcomingMoviesAdapter(val singleLine: Boolean) :
    PagedListAdapter<MovieModel, BindableViewHolder<MovieModel>>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder<MovieModel> {
        if(viewType == SINGLE_LINE){
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_upcoming_movies_single_line, parent, false)
            return SingleLineUpcomingMoviesViewHolder(view)
        }else{
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_upcoming_movies_grid_view, parent, false)
            return GridViewUpcomingMoviesViewHolder(view)

        }

    }

    override fun onBindViewHolder(holder: BindableViewHolder<MovieModel>, position: Int) {
        val movie = getItem(position)
        if (movie == null) {
            holder.clear()
        } else {
            holder.bind(movie)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (singleLine) {
            SINGLE_LINE
        } else {
            GRID_VIEW
        }
    }

//    fun addMovies(movies: Array<MovieModel>) {
//        val newFirstPosition = itemCount + 1
//        this.movies.addAll(movies)
//        notifyItemRangeInserted(newFirstPosition, itemCount)
//    }

//    fun clear() {
//        val lastItem = itemCount
//        notifyItemRangeRemoved(0, lastItem)
//    }

    companion object {
        const val SINGLE_LINE = 0
        const val GRID_VIEW = 1
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<MovieModel>() {
            override fun areItemsTheSame(
                oldConcert: MovieModel,
                newConcert: MovieModel
            ) = oldConcert.id == newConcert.id

            override fun areContentsTheSame(
                oldConcert: MovieModel,
                newConcert: MovieModel
            ) = oldConcert == newConcert
        }
    }
}

