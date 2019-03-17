package com.may.tmdb.movie.upcoming

import com.may.tmdb.movie.upcoming.detail.MovieDetails
import com.may.tmdb.movie.upcoming.detail.MovieDetailsPresenter
import io.mockk.Called
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class MovieDetailsPresenterTest {
    lateinit var mView: MovieDetails.View
    lateinit var mPresenter: MovieDetails.Presenter
    @Before
    fun setup() {
        mView = spyk()
        mPresenter = MovieDetailsPresenter()
        mPresenter.subscribe(mView)
    }

    @Test
    fun `given exist posterUrl when onStart then call setPoster`() {
        val posterUrl = "https://image.tmdb.org/t/p/w600/gdwagnVN7Dt0Xr78gnJepRsRLaLYklbY.jpg"
        val movie = MovieModelBuilder()
            .thumbnailPosterPath(posterUrl)
            .build()

        mPresenter.onStart(movie)

        verify {
            mView.setPoster(posterUrl)
        }
    }

    @Test
    fun `given exist backdropUrl when onStart then call setBackdrop`() {
        val backdrop = "https://image.tmdb.org/t/p/w600/gdwagnVN7Dt0Xr78gnJepRsRLaLYklbY.jpg"
        val movie = MovieModelBuilder()
            .backdropUrl(backdrop)
            .build()

        mPresenter.onStart(movie)

        verify {
            mView.setBackdrop(backdrop)
        }
    }

    @Test
    fun `given no thumbnailPosterUrl and largeBackdropUrl when onStart then no call setPoster and setBackdrop`() {
        val movie = MovieModelBuilder()
            .build()

        mPresenter.onStart(movie)
        // only calls showTitle
        verify(exactly = 1) {
            mView.setTitle(any())
        }
    }

    @Test
    fun `given title when onStart then call setTitle`() {
        val title = "Dumbo"

        val movie = MovieModelBuilder()
            .title(title)
            .build()

        mPresenter.onStart(movie)
        // only calls showTitle
        verify(exactly = 1) {
            mView.setTitle(title)
        }
    }
}