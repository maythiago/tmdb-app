package com.may.tmdb.upcoming

import com.may.tmdb.configuration.ConfigurationImageModel
import com.may.tmdb.configuration.ConfigurationModel
import com.may.tmdb.repository.NetworkRepository
import com.may.tmdb.repository.SharedPreferenceRepository
import io.mockk.Called
import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class UpcomingMoviesPresenterTest {

    lateinit var mPresenter: UpcomingMovies.Presenter
    lateinit var mSharedPreferencesRepository: SharedPreferenceRepository
    lateinit var mNetworkRepository: NetworkRepository

    @Before
    fun setup() {
        mSharedPreferencesRepository = spyk()
        mNetworkRepository = spyk()
        mPresenter = UpcomingMoviesPresenter(mSharedPreferencesRepository, mNetworkRepository)
    }

    @Test
    fun `when has no configuration get in api`() {
        //given
        val imageConfiguration = ConfigurationImageModel("teste_1234", arrayOf())
        val configuration = ConfigurationModel(imageConfiguration)
        every { mNetworkRepository.getConfiguration() } returns Single.just(configuration)
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
        val imageConfiguration = ConfigurationImageModel("teste_1234", arrayOf())
        val configuration = ConfigurationModel(imageConfiguration)
        every { mNetworkRepository.getConfiguration() } returns Single.just(configuration)
        every { mSharedPreferencesRepository.getConfiguration() } returns configuration

        //when
        mPresenter.onStart()

        //then
        verify {
            mNetworkRepository.getConfiguration() wasNot Called
        }
    }
}