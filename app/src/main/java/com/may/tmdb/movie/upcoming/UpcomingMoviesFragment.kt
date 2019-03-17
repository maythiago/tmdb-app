package com.may.tmdb.movie.upcoming

import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.may.tmdb.R
import com.may.tmdb.UpcomingMovieActivity
import com.may.tmdb.movie.MovieModel
import kotlinx.android.synthetic.main.fragment_upcoming_movies.*
import org.koin.android.ext.android.inject
import timber.log.Timber


class UpcomingMoviesFragment : Fragment(), UpcomingMovies.View {
    private val singleLine
        get() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    private val mAdapter by lazy { UpcomingMoviesAdapter(singleLine) }
    private val mPresenter: UpcomingMovies.Presenter by inject()

    override fun showNotFoundServiceError() {
        Toast.makeText(activity, "Ocorreu um erro ao sincronizar os filmes.", Toast.LENGTH_LONG).show()
    }

    override fun showConfigurationError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun showMovies(results: PagedList<MovieModel>) {
        clUpcomingMoviesEmptyState.visibility = View.GONE
        mAdapter.submitList(results)
    }

    override fun removeAllMovies() {
    }

    override fun openMovieDetails(position: Int, movie: MovieModel) {
        val tmdbActivity = activity as UpcomingMovieActivity
        val layoutManager = rvUpcomingMovies.layoutManager
        val clickedView = layoutManager!!.findViewByPosition(position)!!
        val imageView = clickedView.findViewById<ImageView>(R.id.ivUpcomingMoviesPoster)
        tmdbActivity.goToDetailsFragment(movie, imageView)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_upcoming_movies, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("onViewCreated")
        rvUpcomingMovies.adapter = mAdapter
        mAdapter.setOnClickListener { position, movie ->
            mPresenter.handleMovieClicked(position, movie)
        }
        val layoutManager = if (singleLine) {
            rvUpcomingMovies.addItemDecoration(
                DividerItemDecoration(
                    rvUpcomingMovies.context,
                    DividerItemDecoration.VERTICAL
                )
            )
            LinearLayoutManager(rvUpcomingMovies.context)
        } else {
            GridLayoutManager(rvUpcomingMovies.context, 4)
        }
        rvUpcomingMovies.layoutManager = layoutManager
        mPresenter.subscribe(this)
        mPresenter.onStart()
    }

    override fun onDestroyView() {
        mPresenter.unsubscribe()
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            UpcomingMoviesFragment()

    }
}

