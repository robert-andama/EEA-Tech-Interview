package com.engie.eea_tech_interview.utils

object AppConstants{
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val IMAGE_ENDPOINT_PREFIX = "https://image.tmdb.org/t/p/w500/"
    const val MOVIE_API_KEY = "c9a058c168b2ee5563bada0f30c7b0e8"
    const val SEARCH_QUERY = "James Bond"
    const val SEARCH_QUERY_INVALID = "QQQQQQ"
    const val MOVIE_ID_VALID = "436270"
    const val MOVIE_ID_INVALID = "436270123131313"
    const val CACHE_SIZE: Long = 10 * 1024 * 1024 // 10MB
    const val READ_TIME_OUT: Long = 30
    const val WRITE_TIME_OUT: Long = 10
    const val CONNECT_TIME_OUT: Long = 10
}