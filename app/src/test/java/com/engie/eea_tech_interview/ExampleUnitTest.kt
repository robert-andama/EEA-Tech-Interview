package com.engie.eea_tech_interview

import com.engie.eea_tech_interview.model.GenreResult
import com.engie.eea_tech_interview.model.SearchResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class ExampleUnitTest {

    @Mock
    private lateinit var moviesSearchResult: SearchResult

    @Mock
    private lateinit var movieGenresResult: GenreResult

    private val apiKey = "apiKey"
    private val query = "captain america"

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
//        appViewModel = AppViewModel(movieRepository)
    }

    @Test
    fun test_getMoviesFromNetWorkRepository() = runTest {
        //arrange
//        val expected = flow {
//            emit(Resource.loading())
//            delay(10)
//            emit(Resource.success(moviesSearchResult.results))
//        }
//
//        val result = Result.success(moviesSearchResult)
//        whenever(movieRepository.getMovies(apiKey, query)).thenReturn(result)
//
//        //act
//        val actual = appViewModel.movies
//
//        //assert
//        assertEquals(expected.first().status, actual.first().status)
//        assertEquals(expected.last().status, actual.last().status)
//        assertEquals(expected.last().data, actual.last().data)
//        verify(movieRepository).getMovies(any(), any())
    }
}