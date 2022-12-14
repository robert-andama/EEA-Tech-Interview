package com.engie.eea_tech_interview.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResult(
    @Json(name = "results") val results: List<Movie>
)