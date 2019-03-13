package com.may.tmdb.movie.upcoming

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.may.tmdb.movie.MovieModel
import kotlinx.android.synthetic.main.fragment_upcoming_movies.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class UpcomingMoviesFragment : Fragment(), UpcomingMovies.View {
    override fun showNotFoundServiceError() {
        Toast.makeText(activity, "Ocorreu um erro ao sincronizar os filmes.", Toast.LENGTH_LONG).show()
    }

    private val mAdapter by lazy { UpcomingMoviesAdapter() }
    private val mPresenter: UpcomingMovies.Presenter by inject()


    override fun showConfigurationError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun showMovies(results: Array<MovieModel>) {
        mAdapter.addMovies(results)
    }

    override fun removeAllMovies() {
        mAdapter.clear()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.i("onCreateView $savedInstanceState")
        return inflater.inflate(com.may.tmdb.R.layout.fragment_upcoming_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvUpcomingMovies.adapter = mAdapter
        val orientation = resources.configuration.orientation;
        val columnCount = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            4
        } else {
            2
        }
        rvUpcomingMovies.layoutManager = GridLayoutManager(rvUpcomingMovies.context, columnCount)
        Timber.i("onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        mPresenter.subscribe(this)
        mPresenter.onStart()

        Timber.i("onViewCreated")
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

