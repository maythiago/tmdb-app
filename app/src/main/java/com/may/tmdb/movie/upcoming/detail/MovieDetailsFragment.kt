package com.may.tmdb.movie.upcoming.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.may.tmdb.R
import com.may.tmdb.movie.MovieModel
import com.may.tmdb.movie.upcoming.PosterProvider
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.security.InvalidParameterException

class MovieDetailsFragment : Fragment(), MovieDetails.View {
    private val mPresenter: MovieDetails.Presenter by inject()
    private val movie
        get() = arguments?.getParcelable<MovieModel>(ARG_MOVIE_MODEL) ?: throw InvalidParameterException()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.i("onCreateView $savedInstanceState")
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onStart() {
        super.onStart()
        mPresenter.subscribe(this)
        mPresenter.onStart(movie)
    }
    override fun onStop() {
        mPresenter.unsubscribe()
        super.onStop()
    }

    override fun setPoster(posterPath: String?) {
        Timber.i("posterPath=$posterPath")

        PosterProvider.load(ivMovieDetailPoster.context, posterPath, ivMovieDetailPoster)
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
