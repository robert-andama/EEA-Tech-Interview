package com.engie.eea_tech_interview

import com.engie.eea_tech_interview.api.MovieApiService
import com.engie.eea_tech_interview.model.Movie
import com.engie.eea_tech_interview.model.SearchResult
import com.engie.eea_tech_interview.network.Resource
import com.engie.eea_tech_interview.network.ResponseHandler
import com.engie.eea_tech_interview.repository.MovieRepository
import com.engie.eea_tech_interview.utils.AppConstants
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException

@RunWith(JUnit4::class)
class MovieListRepositoryTest {
    private val responseHandler = ResponseHandler()
    private lateinit var movieApiService: MovieApiService
    private lateinit var repository: MovieRepository
    private val validSearchQuery = AppConstants.SEARCH_QUERY
    private val invalidSearchQuery = AppConstants.SEARCH_QUERY_INVALID
    private val results = Movie(1, "some_path","overview","date","original_title", listOf(),"","","title",0,false)
    private val searchResult = SearchResult(listOf(results))
    private val searchResultResource = Resource.success(searchResult)
    private val errorResponse = Resource.error("Unauthorised", null)

    @Before
    fun setUp() {
        movieApiService = mock()
        val mockException: HttpException = mock()
        whenever(mockException.code()).thenReturn(401)
        runBlocking {
            whenever(movieApiService.getMovies(eq(invalidSearchQuery))).thenThrow(mockException)
            whenever(movieApiService.getMovies(eq(validSearchQuery))).thenReturn(searchResult)
        }
        repository = MovieRepository(
            movieApiService,
            responseHandler
        )
    }

    @Test
    fun `test getMovie when valid movie search is requested, then movie results is returned`() =
        runBlocking {
            assertEquals(searchResultResource, repository.getMovie(validSearchQuery))
        }

    @Test
    fun `test getMovie when invalid  movie search is requested, then error response is returned`() =
        runBlocking {
            assertEquals(errorResponse, repository.getMovie(invalidSearchQuery))
        }
}