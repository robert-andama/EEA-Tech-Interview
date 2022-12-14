package com.engie.eea_tech_interview

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.engie.eea_tech_interview.model.Movie
import com.engie.eea_tech_interview.model.SearchResult
import com.engie.eea_tech_interview.network.Resource
import com.engie.eea_tech_interview.repository.MovieRepository
import com.engie.eea_tech_interview.utils.AppConstants
import com.engie.eea_tech_interview.viewmodel.MovieViewModel
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class MovieListViewModelTest {
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieRepository: MovieRepository
    private lateinit var movieObserver: Observer<Resource<SearchResult>>
    private val validSearchQuery = AppConstants.SEARCH_QUERY
    private val invalidSearchQuery = AppConstants.SEARCH_QUERY_INVALID
    private val results = Movie(1, "some_path","overview","date","original_title", listOf(),"","","title",0,false)
    private val successResource = Resource.success(SearchResult(listOf(results)))
    private val errorResource = Resource.error("Unauthorised", null)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(DelicateCoroutinesApi::class)
    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        movieRepository = mock()
        runBlocking {
            whenever(movieRepository.getMovie(validSearchQuery)).thenReturn(successResource)
            whenever(movieRepository.getMovie(invalidSearchQuery)).thenReturn(errorResource)
        }
        movieViewModel = MovieViewModel(movieRepository)
        movieObserver = mock()
    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `when getMovie is called with valid movie search, then observer is updated with success`() = runBlocking {
        movieViewModel.movieQuery.observeForever(movieObserver)
        movieViewModel.getSearchQuery(validSearchQuery)
        delay(10)
        verify(movieRepository).getMovie(validSearchQuery)
        verify(movieObserver, timeout(50)).onChanged(Resource.loading(null))
        verify(movieObserver, timeout(50)).onChanged(successResource)
    }

    @Test
    fun `when getMovie is called with invalid movie search, then observer is updated with failure`() = runBlocking {
        movieViewModel.movieQuery.observeForever(movieObserver)
        movieViewModel.getSearchQuery(invalidSearchQuery)
        delay(10)
        verify(movieRepository).getMovie(invalidSearchQuery)
        verify(movieObserver, timeout(50)).onChanged(Resource.loading(null))
        verify(movieObserver, timeout(50)).onChanged(errorResource)
    }
}