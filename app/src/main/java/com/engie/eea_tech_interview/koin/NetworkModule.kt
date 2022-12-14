package com.engie.eea_tech_interview.koin

import com.engie.eea_tech_interview.network.createOkHttpClient
import com.engie.eea_tech_interview.network.createMoshiConverter
import com.engie.eea_tech_interview.network.createRetrofit
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkModule = module(createdAtStart = true) {
    single {
        val baseUrl = "https://api.themoviedb.org/3/"
        createRetrofit(baseUrl, get(), get())
    }

    single { createOkHttpClient(androidContext()) }

    single { createMoshiConverter() }
}