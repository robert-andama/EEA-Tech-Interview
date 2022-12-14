package com.engie.eea_tech_interview.network

import com.engie.eea_tech_interview.utils.AppConstants.MOVIE_API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("api_key", MOVIE_API_KEY).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}