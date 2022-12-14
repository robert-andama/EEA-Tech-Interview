package com.engie.eea_tech_interview.koin

import com.engie.eea_tech_interview.viewmodel.MovieViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { MovieViewModel(get()) }
}