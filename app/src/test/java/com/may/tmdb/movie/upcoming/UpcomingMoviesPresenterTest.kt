package com.may.tmdb.movie.upcoming

import androidx.paging.PagedList
import com.may.tmdb.base.PaginatedResponse
import com.may.tmdb.configuration.ConfigurationImageModel
import com.may.tmdb.configuration.ConfigurationModel
import com.may.tmdb.movie.MovieModel
import com.may.tmdb.repository.network.NetworkRepository
import com.may.tmdb.repository.SharedPreferenceRepository
import io.mockk.*
import io.reactivex.Single
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.mock.MockRetrofit

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
        val imageConfiguration = ConfigurationImageModel("teste_1234", "teste_1234", arrayOf())
        val configuration = ConfigurationModel(imageConfiguration)
        every { mNetworkRepository.getConfiguration() } returns Single.just(configuration)
        every { mSharedPreferencesRepository.getConfiguration() } returns null
        // TODO Quebrar verificação de configuração para outra classe para evitar isso
        val paginatedResponse = PaginatedResponse(1, arrayOf<MovieModel>(), 3, 3)
        every { mNetworkRepository.getUpcomingMovie() } returns Single.just(paginatedResponse)

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
        val imageConfiguration = ConfigurationImageModel("teste_1234", "teste_1234", arrayOf())
        val configuration = ConfigurationModel(imageConfiguration)
        every { mNetworkRepository.getConfiguration() } returns Single.just(configuration)
        every { mSharedPreferencesRepository.getConfiguration() } returns configuration
        // TODO Quebrar verificação de configuração para outra classe para evitar isso
        val paginatedResponse = PaginatedResponse(1, arrayOf<MovieModel>(), 3, 3)
        every { mNetworkRepository.getUpcomingMovie() } returns Single.just(paginatedResponse)
        //when
        mPresenter.onStart()

        //then
        verify {
            mNetworkRepository.getConfiguration() wasNot Called
        }
    }

    @Test
    fun `get movie and show in view`() {
        //given
        val imageConfiguration = ConfigurationImageModel("teste_1234", "teste_1234", arrayOf())
        val configuration = ConfigurationModel(imageConfiguration)
        val movie1 = MovieModelBuilder().build()
        val movie2 = MovieModelBuilder().build()
        val movie3 = MovieModelBuilder().build()
        val movies = arrayOf(movie1, movie2, movie3)
        val paginatedResponse = PaginatedResponse(1, movies, 3, 3)
        every { mNetworkRepository.getConfiguration() } returns Single.just(configuration)
        every { mNetworkRepository.getUpcomingMovie() } returns Single.just(paginatedResponse)
        every { mSharedPreferencesRepository.getConfiguration() } returns configuration
        val slotMovies = slot<PagedList<MovieModel>>()
        every { mView.showMovies(capture(slotMovies)) } just Runs

        //when
        mPresenter.onStart()

        //then
//        verify(exactly = 1) {
//            mView.showMovies(movies)
//        }
        for (index in 0 until slotMovies.captured.size) {
            Assert.assertEquals(slotMovies.captured[index]!!.id, movies[index].id)
        }
    }

    @Test
    fun `when get 404 error show a message`() {
        //given
        val imageConfiguration = ConfigurationImageModel("teste_1234", "teste_1234", arrayOf())
        val configuration = ConfigurationModel(imageConfiguration)
        val movie1 = MovieModelBuilder().build()
        val movie2 = MovieModelBuilder().build()
        val movie3 = MovieModelBuilder().build()
        val movies = arrayOf(movie1, movie2, movie3)
        val paginatedResponse = PaginatedResponse(1, movies, 3, 3)
        every { mNetworkRepository.getConfiguration() } returns Single.just(configuration)
        every { mSharedPreferencesRepository.getConfiguration() } returns configuration
        val httpException = HttpExceptionBuilder()
            .code(404)
            .build()

        every { mNetworkRepository.getUpcomingMovie() } returns Single.error(httpException)
        //when
        mPresenter.onStart()

        //then
        verify(exactly = 1) {
            mView.showNotFoundServiceError()
        }
    }

    @Test
    fun `open details when movie clicked`() {
        val movie = MovieModelBuilder().build()

        mPresenter.handleMovieClicked(movie)

        verify(exactly = 1) {
            mView.openMovieDetails(movie)
        }
    }

    @Test
    fun `onRefresh call invalidate`() {

        mPresenter.onRefreshListener()

        verify(exactly = 1) {
            mView.showEmptyState()
            mNetworkRepository.invalidateData()
        }
    }


}