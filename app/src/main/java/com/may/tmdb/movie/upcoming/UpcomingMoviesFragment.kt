package com.may.tmdb.movie.upcoming

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.may.tmdb.movie.MovieModel
import kotlinx.android.synthetic.main.fragment_upcoming_movies.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class UpcomingMoviesFragment : Fragment(), UpcomingMovies.View {
    private val singleLine
        get() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    private val mAdapter by lazy { UpcomingMoviesAdapter(singleLine) }
    private val mPresenter: UpcomingMoviesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.onStart()

        mPresenter.configuration.observe(this@UpcomingMoviesFragment, Observer {
            Timber.i("configuração foi carregada $it")
            mPresenter.movies.observe(this@UpcomingMoviesFragment, Observer {
                Timber.i("Carregando filmes")
                clUpcomingMoviesEmptyState.visibility = View.GONE
                mAdapter.submitList(it)
            })
        })
        mPresenter.error.observe(this@UpcomingMoviesFragment, Observer {
            showConfigurationError(it)
        })
    }

    override fun showNotFoundServiceError() {
        Toast.makeText(activity, "Ocorreu um erro ao sincronizar os filmes.", Toast.LENGTH_LONG).show()
    }

    override fun showConfigurationError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun showMovies(results: Array<MovieModel>) {
    }


    override fun removeAllMovies() {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.i("onCreateView $savedInstanceState")
        return inflater.inflate(com.may.tmdb.R.layout.fragment_upcoming_movies, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvUpcomingMovies.adapter = mAdapter
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

    override fun onStart() {
        super.onStart()
//        mPresenter.subscribe(this)
    }

    override fun onStop() {
//        mPresenter.unsubscribe()
        super.onStop()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            UpcomingMoviesFragment()
    }
}

