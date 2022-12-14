package com.engie.eea_tech_interview.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "id") val id: Int,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "overview") val overview: String?,
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "original_title") val originalTitle: String?,
    @Json(name = "genre_ids") val genreIds: List<Int>?,
    @Json(name = "media_type") val mediaType: String?,
    @Json(name = "original_language") val originalLanguage: String?,
    @Json(name = "title") val title: String?,
    @Json(name = "vote_count") val voteCount: Int?,
    @Json(name = "video") val hasVideo: Boolean?,
)