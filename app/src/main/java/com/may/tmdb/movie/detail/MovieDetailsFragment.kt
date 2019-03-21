package com.may.tmdb.movie.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.may.tmdb.movie.MovieModel
import com.may.tmdb.movie.upcoming.PosterProvider
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.security.InvalidParameterException
import android.widget.Toast
import com.may.tmdb.R
import kotlinx.android.synthetic.main.fragment_movie_detail.view.*


class MovieDetailsFragment : Fragment(), MovieDetails.View {
    override fun setOverview(overview: String) {
        tvMovieDetailOverview.text = overview
    }

    override fun setTitle(title: String) {
        collapsingToolbar.title = title
        tvMovieDetailTitle.text = title
    }

    override fun showProgress() {
        pbMovieDetailLoading.visibility = VISIBLE
    }

    override fun hideProgress() {
        pbMovieDetailLoading.visibility = GONE

    }

    override fun setGenre(genre: String) {
        tvMovieDetailCategory.text = genre
    }


    private val mPresenter: MovieDetails.Presenter by inject()
    private val movie
        get() = arguments?.getParcelable<MovieModel>(ARG_MOVIE_MODEL) ?: throw InvalidParameterException()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.i("onCreateView $savedInstanceState")

        val view = inflater.inflate(R.layout.fragment_movie_detail, container, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.ivMovieDetailPoster.setTransitionName("moviePoster");
        }
        view.ivMovieDetailPoster.setOnClickListener {
            mPresenter.onPosterClicked(movie)
        }

        val detailActivity = activity as DetailActivity
        detailActivity.setSupportActionBar(view.toolbar)
        detailActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return view
    }

    override fun openPosterFullScreen(posterUrl: String) {
        val detailActivity = activity as DetailActivity
        detailActivity.openFullScreenImage(posterUrl)
    }

    override fun setReleaseDate(releaseDate: String) {
        tvMovieDetailReleaseDate.text = releaseDate
    }


    override fun onStart() {
        super.onStart()
        mPresenter.subscribe(this)
        mPresenter.onStart(movie)

    }

    override fun showEmptyOverview() {
        setOverview(getString(R.string.no_overview_founded))
    }

    override fun onStop() {
        mPresenter.unsubscribe()
        super.onStop()
    }

    override fun setPoster(posterPath: String) {
        PosterProvider
            .load(ivMovieDetailPoster.context, posterPath, ivMovieDetailPoster)
    }

    companion object {
        fun newInstance(movie: MovieModel): Fragment {
            val bundle = Bundle()
            bundle.putParcelable(ARG_MOVIE_MODEL, movie)
            return MovieDetailsFragment()
                .apply {
                    arguments = bundle
                }
        }

        const val ARG_MOVIE_MODEL = "ARG_MOVIE_MODEL"
    }
}
