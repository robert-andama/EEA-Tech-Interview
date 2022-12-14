package com.engie.eea_tech_interview

import com.engie.eea_tech_interview.api.MovieApiService
import com.engie.eea_tech_interview.model.MovieDetailsResults
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
import org.mockito.Mockito
import org.mockito.Mockito.*
import retrofit2.HttpException

@RunWith(JUnit4::class)
class MovieDetailsRepositoryTest {
    private val responseHandler = ResponseHandler()
    private lateinit var movieApiService: MovieApiService
    private lateinit var repository: MovieRepository
    private val validMovieId = AppConstants.MOVIE_ID_VALID
    private val invalidMovieId = AppConstants.MOVIE_ID_INVALID
    private val detailsResults = MovieDetailsResults("title","overview","some_path", listOf(),"date",2.2,1)
    private val searchResultResource = Resource.success(detailsResults)
    private val errorResponse = Resource.error("Unauthorised", null)

    @Before
    fun setUp() {
        movieApiService = mock()
        val mockException: HttpException = mock()
        whenever(mockException.code()).thenReturn(401)
        runBlocking {
            whenever(movieApiService.getMovieDetails(eq(invalidMovieId))).thenThrow(mockException)
            whenever(movieApiService.getMovieDetails(eq(validMovieId))).thenReturn(detailsResults)
        }
        repository = MovieRepository(
            movieApiService,
            responseHandler
        )
    }

    @Test
    fun `test getMovieDetails when valid movie id is passed, then movie details results is returned`() =
        runBlocking {
            assertEquals(searchResultResource, repository.getMovieDetails(validMovieId.toString()))
        }

    @Test
    fun `test getMovieDetails when invalid movie id passed is requested, then error response is returned`() =
        runBlocking {
            assertEquals(errorResponse, repository.getMovieDetails(invalidMovieId.toString()))
        }
}