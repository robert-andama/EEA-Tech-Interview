package com.engie.eea_tech_interview.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailsResults(
    val title: String,
    val overview: String,
    @Json(name = "poster_path")
    val posterPath: String?,
    val genres: List<Genre>,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int,
) {
    @JsonClass(generateAdapter = true)
    data class Genre(
        val id: Int,
        val name: String,
    )
}