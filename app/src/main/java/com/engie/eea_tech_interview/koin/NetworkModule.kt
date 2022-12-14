package com.engie.eea_tech_interview.koin

import com.engie.eea_tech_interview.network.*
import com.engie.eea_tech_interview.utils.AppConstants
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkModule = module {
    factory { AuthInterceptor() }
    single { provideOkHttpClient(androidContext(), get()) }
    factory { provideMovieApi(get()) }
    factory { provideLoggingInterceptor() }
    single { provideRetrofit(AppConstants.BASE_URL, get(), get()) }
    factory { ResponseHandler() }
    single { provideMoshiConverter() }
}