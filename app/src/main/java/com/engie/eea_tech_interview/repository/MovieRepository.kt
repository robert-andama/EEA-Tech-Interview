package com.engie.eea_tech_interview.repository

import com.engie.eea_tech_interview.api.MovieApiService
import com.engie.eea_tech_interview.model.MovieDetailsResults
import com.engie.eea_tech_interview.model.SearchResult
import com.engie.eea_tech_interview.network.Resource
import com.engie.eea_tech_interview.network.ResponseHandler
import org.koin.dsl.module



open class MovieRepository(
    private val movieApiService: MovieApiService,
    private val responseHandler: ResponseHandler,
) {

    suspend fun getMovie(searchQuery: String): Resource<SearchResult> {
        return try {
            val response = movieApiService.getMovies(searchQuery)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun getMovieDetails(movieId: String): Resource<MovieDetailsResults> {
        return try {
            val response = movieApiService.getMovieDetails(movieId.toInt())
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}