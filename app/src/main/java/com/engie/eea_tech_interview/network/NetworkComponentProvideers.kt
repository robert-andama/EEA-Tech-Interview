package com.engie.eea_tech_interview.network

import android.content.Context
import com.squareup.moshi.Moshi
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

const val CACHE_SIZE: Long = 10 * 1024 * 1024 // 10MB
const val READ_TIME_OUT: Long = 30
const val WRITE_TIME_OUT: Long = 10
const val CONNECT_TIME_OUT: Long = 10

fun createRetrofit(
    baseUrl: String,
    converterFactory: Converter.Factory,
    client: OkHttpClient
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(converterFactory)
        .build()
}

fun createOkHttpClient(context: Context): OkHttpClient {

    val clientBuilder = OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
        .cache(Cache(context.cacheDir, CACHE_SIZE))

    return clientBuilder.build()
}

fun createMoshiConverter(): Converter.Factory =
    MoshiConverterFactory.create(Moshi.Builder().build())