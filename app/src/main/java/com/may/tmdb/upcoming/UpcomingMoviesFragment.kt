package com.may.tmdb.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.may.tmdb.R
import org.koin.android.ext.android.inject

class UpcomingMoviesFragment : Fragment(), UpcomingMovies.View {
    override fun showConfigurationError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    private val mPresenter: UpcomingMovies.Presenter by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_upcoming_movies, container, false)
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
