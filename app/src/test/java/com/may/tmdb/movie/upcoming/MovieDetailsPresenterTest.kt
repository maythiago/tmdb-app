package com.may.tmdb.movie.upcoming

import com.may.tmdb.movie.detail.MovieDetails
import com.may.tmdb.movie.detail.MovieDetailsPresenter
import com.may.tmdb.movie.genre.GenreModel
import com.may.tmdb.movie.genre.GenreResponseModel
import com.may.tmdb.repository.network.NetworkRepository
import io.mockk.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class MovieDetailsPresenterTest {
    lateinit var mView: MovieDetails.View
    lateinit var mPresenter: MovieDetails.Presenter
    lateinit var mNetworkRepository: NetworkRepository

    @Before
    fun setup() {
        mView = spyk()
        mNetworkRepository = mockk()
        mPresenter = MovieDetailsPresenter(mNetworkRepository)
        mPresenter.subscribe(mView)
    }

    @Test
    fun `given exist posterUrl when onStart then call setPoster`() {
        val posterUrl = "https://image.tmdb.org/t/p/w600/gdwagnVN7Dt0Xr78gnJepRsRLaLYklbY.jpg"
        val movie = MovieModelBuilder()
            .thumbnailPosterPath(posterUrl)
            .build()
        every { mNetworkRepository.getGenres() } returns Single.never()

        mPresenter.onStart(movie)

        verify {
            mView.setPoster(posterUrl)
        }
    }

    @Test
    fun `given no thumbnailPosterUrl when onStart then no call setPoster`() {
        val movie = MovieModelBuilder()
            .build()
        every { mNetworkRepository.getGenres() } returns Single.never()

        mPresenter.onStart(movie)

        // only calls showTitle
        verify(exactly = 1) {
            mView.setTitle(any())
        }
    }

    @Test
    fun `when onStart then call setTitle`() {
        val title = "Dumbo"
        val movie = MovieModelBuilder()
            .title(title)
            .build()
        every { mNetworkRepository.getGenres() } returns Single.never()

        mPresenter.onStart(movie)

        // only calls showTitle
        verify(exactly = 1) {
            mView.setTitle(title)
        }
    }

    @Test
    fun `when onStart then load and set genre`() {
        val movie = MovieModelBuilder()
            .genreIds(intArrayOf(0, 2, 4))
            .build()
        val genres = arrayOf(
            GenreModel(0, "Ação"),
            GenreModel(2, "Comédia"),
            GenreModel(4, "Terror")
        )
        val response = GenreResponseModel(genres)
        every { mNetworkRepository.getGenres() } returns Single.just(response)

        mPresenter.onStart(movie)

        // only calls showTitle
        verify(exactly = 1) {
            mView.setGenre("Ação, Comédia, Terror")
        }
    }

    @Test
    fun `when onStart then load and setReleaseDate`() {
        val releaseDate = "13-12-2019"
        val movie = MovieModelBuilder()
            .releaseDate(releaseDate)
            .build()
        every { mNetworkRepository.getGenres() } returns Single.never()

        mPresenter.onStart(movie)

        // only calls showTitle
        verify(exactly = 1) {
            mView.setReleaseDate(releaseDate)
        }
    }

    @Test
    fun `when onStart then load and setOverview`() {
        val overview = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut"
        val movie = MovieModelBuilder()
            .overview(overview)
            .build()
        every { mNetworkRepository.getGenres() } returns Single.never()

        mPresenter.onStart(movie)

        // only calls showTitle
        verify(exactly = 1) {
            mView.setOverview(overview)
        }
    }
    @Test
    fun `when onStart then load and show empty overview`() {
        val movie = MovieModelBuilder()
            .build()
        every { mNetworkRepository.getGenres() } returns Single.never()

        mPresenter.onStart(movie)

        // only calls showTitle
        verify(exactly = 1) {
            mView.showEmptyOverview()
        }
    }
}