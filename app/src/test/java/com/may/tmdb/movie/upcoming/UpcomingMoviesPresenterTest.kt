package com.may.tmdb.movie.upcoming

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.may.tmdb.base.PaginatedResponse
import com.may.tmdb.configuration.ConfigurationImageModel
import com.may.tmdb.configuration.ConfigurationModel
import com.may.tmdb.mock.MockDataSourceFactory
import com.may.tmdb.movie.MovieModel
import com.may.tmdb.repository.network.NetworkRepository
import com.may.tmdb.repository.SharedPreferenceRepository
import io.mockk.*
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UpcomingMoviesPresenterTest {

    lateinit var mPresenter: UpcomingMovies.Presenter
    lateinit var mSharedPreferencesRepository: SharedPreferenceRepository
    lateinit var mNetworkRepository: NetworkRepository
    lateinit var mView: UpcomingMovies.View

    @Before
    fun setup() {
        mSharedPreferencesRepository = spyk()
        mNetworkRepository = spyk()
        mView = spyk()
        mPresenter = UpcomingMoviesPresenter(mSharedPreferencesRepository, mNetworkRepository)
        mPresenter.subscribe(mView)
    }

    @Test
    fun `when has no configuration get in api`() {
        //given
        val imageConfiguration = ConfigurationImageModel("teste_1234", "teste_1234", arrayOf(), arrayOf())
        val configuration = ConfigurationModel(imageConfiguration)
        every { mNetworkRepository.getConfiguration() } returns Single.just(configuration)
        every { mNetworkRepository.getPagingUpcomingMovie(any()) } returns Observable.never<PagedList<MovieModel>>()
        every { mSharedPreferencesRepository.getConfiguration() } returns null

        //when
        mPresenter.onStart()

        //then
        verify {
            mSharedPreferencesRepository.setConfiguration(configuration)
        }
    }

    @Test
    fun `return cached configuration when exists`() {
        //given
        val imageConfiguration = ConfigurationImageModel("teste_1234", "teste_1234", arrayOf(), arrayOf())
        val configuration = ConfigurationModel(imageConfiguration)
        every { mNetworkRepository.getConfiguration() } returns Single.just(configuration)
        every { mNetworkRepository.getPagingUpcomingMovie(any()) } returns Observable.never<PagedList<MovieModel>>()
        every { mSharedPreferencesRepository.getConfiguration() } returns configuration

        //when
        mPresenter.onStart()

        //then
        verify {
            mNetworkRepository.getConfiguration() wasNot Called
        }
    }

    @Test
    fun `given error on getConfiguration then show message and emptyState`() {
        //given
        val imageConfiguration = ConfigurationImageModel("teste_1234", "teste_1234", arrayOf(), arrayOf())
        val configuration = ConfigurationModel(imageConfiguration)
        every { mNetworkRepository.getConfiguration() } returns Single.error(Throwable())
        every { mSharedPreferencesRepository.getConfiguration() } returns null

        //when
        mPresenter.onStart()

        //then
        verify {
            mView.showEmptyState()
            mView.showConfigurationError()
        }
    }

    @Test
    fun `open details when movie clicked`() {
        val movie = MovieModelBuilder().build()

        val position = 2
        mPresenter.handleMovieClicked(position, movie)

        verify(exactly = 1) {
            mView.openMovieDetails(position, movie)
        }
    }

    @Test
    fun `onRefresh call invalidate`() {

        mPresenter.onRefreshListener()

        verify(exactly = 1) {
            mNetworkRepository.invalidateData()
        }
    }


}