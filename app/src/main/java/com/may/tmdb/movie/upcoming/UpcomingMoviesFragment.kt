package com.may.tmdb.movie.upcoming

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.may.tmdb.TMDBActivity
import com.may.tmdb.movie.MovieModel
import com.may.tmdb.movie.upcoming.detail.MovieDetailsFragment
import kotlinx.android.synthetic.main.fragment_upcoming_movies.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
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
        srlUpcomingMovieRefresh.isRefreshing = false
        clUpcomingMoviesEmptyState.visibility = View.GONE
        mAdapter.submitList(results)
    }


    override fun removeAllMovies() {
    }

    override fun openMovieDetails(movie: MovieModel) {
        val fragment = MovieDetailsFragment.newInstance(movie)
        val tmdbActivity = activity as TMDBActivity
        tmdbActivity.goToFragment(fragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.i("onCreateView $savedInstanceState")
        return inflater.inflate(com.may.tmdb.R.layout.fragment_upcoming_movies, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvUpcomingMovies.adapter = mAdapter
        mAdapter.setOnClickListener { mPresenter.handleMovieClicked(it) }
        srlUpcomingMovieRefresh.setOnRefreshListener { mPresenter.onRefreshListener() }
        val columnCount = if (singleLine) {
            rvUpcomingMovies.addItemDecoration(
                DividerItemDecoration(
                    rvUpcomingMovies.context,
                    DividerItemDecoration.VERTICAL
                )
            )
            1
        } else {
            4
        }
        rvUpcomingMovies.layoutManager = GridLayoutManager(rvUpcomingMovies.context, columnCount)
    }

    override fun showEmptyState() {
        clUpcomingMoviesEmptyState.visibility = View.VISIBLE
    }

    override fun onStart() {
        super.onStart()
        mPresenter.subscribe(this)
        mPresenter.onStart()
    }

    override fun onStop() {
        mPresenter.unsubscribe()
        super.onStop()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            UpcomingMoviesFragment()
    }
}

