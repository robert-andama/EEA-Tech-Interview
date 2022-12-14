package com.engie.eea_tech_interview.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.engie.eea_tech_interview.network.Resource
import com.engie.eea_tech_interview.repository.MovieRepository
import org.koin.dsl.module


class MovieViewModel(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val searchQuery = MutableLiveData<String>()
    private val movieId = MutableLiveData<String>()

    fun getSearchQuery(input: String) {
        searchQuery.value = input
    }

    fun getMovieId(input: String)  {
        movieId.value = input
    }

    var movieQuery = searchQuery.switchMap { searchQuery ->
        liveData {
            emit(Resource.loading(null))
            emit(movieRepository.getMovie(searchQuery))
        }
    }

    var movieDetails = movieId.switchMap { id ->
        liveData {
            emit(Resource.loading(null))
            emit(movieRepository.getMovieDetails(id))
        }
    }
}
