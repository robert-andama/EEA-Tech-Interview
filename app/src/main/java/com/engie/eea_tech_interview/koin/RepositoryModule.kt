package com.engie.eea_tech_interview.koin

import com.engie.eea_tech_interview.repository.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { MovieRepository(get(), get()) }
}