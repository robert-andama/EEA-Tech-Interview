package com.engie.eea_tech_interview.api

import com.engie.eea_tech_interview.model.GenreResult
import com.engie.eea_tech_interview.model.MovieDetailsResults
import com.engie.eea_tech_interview.model.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("search/movie")
    suspend fun getMovies(@Query("query") query: String): SearchResult

    @GET("genre/movie/list")
    fun getGenre(@Query("api_key") apiKey: String): Call<GenreResult>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): MovieDetailsResults
}