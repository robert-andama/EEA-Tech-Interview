package com.engie.eea_tech_interview.network

import android.content.Context
import com.engie.eea_tech_interview.api.MovieApiService
import com.engie.eea_tech_interview.utils.AppConstants.CACHE_SIZE
import com.engie.eea_tech_interview.utils.AppConstants.CONNECT_TIME_OUT
import com.engie.eea_tech_interview.utils.AppConstants.READ_TIME_OUT
import com.engie.eea_tech_interview.utils.AppConstants.WRITE_TIME_OUT
import com.squareup.moshi.Moshi
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


fun provideRetrofit(
    baseUrl: String,
    client: OkHttpClient,
    converterFactory: Converter.Factory,
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(converterFactory)
        .build()
}

fun provideOkHttpClient(
    context: Context,
    authInterceptor: AuthInterceptor
): OkHttpClient {
    return OkHttpClient()
        .newBuilder()
        .addInterceptor(authInterceptor)
        .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
        .cache(Cache(context.cacheDir, CACHE_SIZE))
        .build()
}

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BASIC
    return logger
}

fun provideMoshiConverter(): Converter.Factory =
    MoshiConverterFactory.create(Moshi.Builder().build())

fun provideMovieApi(retrofit: Retrofit): MovieApiService =
    retrofit.create(MovieApiService::class.java)
