package com.may.tmdb.movie.upcoming

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.may.tmdb.R
import com.may.tmdb.base.BindableViewHolder
import com.may.tmdb.movie.MovieModel

class UpcomingMoviesAdapter : PagedListAdapter<MovieModel, BindableViewHolder<MovieModel>>(DIFF_CALLBACK) {
    //    private val movies: MutableList<MovieModel> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder<MovieModel> {
        return if (viewType == WITH_POSTER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_upcoming_movies_poster, parent, false)
            PosterUpcomingMoviesViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_upcoming_movies_title, parent, false)
            TitleUpcomingMoviesViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: BindableViewHolder<MovieModel>, position: Int) {
        val movie = getItem(position)
        if(movie == null) {
            holder.clear()
        }else {
            holder.bind(movie)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val movie = getItem(position)
        return if (movie?.posterPath == null) {
            WITH_TITLE
        } else {
            WITH_POSTER
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
        const val WITH_POSTER = 0
        const val WITH_TITLE = 1
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

