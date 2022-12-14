package com.engie.eea_tech_interview.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.engie.eea_tech_interview.R
import com.engie.eea_tech_interview.api.MovieApiService
import com.engie.eea_tech_interview.databinding.FragmentMovieDetailsBinding
import com.engie.eea_tech_interview.model.MovieDetailsResults
import com.engie.eea_tech_interview.utils.AppConstants
import com.engie.eea_tech_interview.utils.AppConstants.MOVIE_API_KEY
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val retrofit: Retrofit by inject()
    private val movieApiService: MovieApiService = retrofit.create(MovieApiService::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieID: Int? = arguments?.getInt("movie", 0)

        getMovieDetails(movieID.toString())
    }

    private fun getMovieDetails(movieID: String) {
        movieApiService.getMovieDetails(movieID.toInt(), MOVIE_API_KEY)
            .enqueue(object : Callback<MovieDetailsResults> {
                override fun onResponse(
                    call: Call<MovieDetailsResults>,
                    response: Response<MovieDetailsResults>,
                ) {
                    val searchResult = response.body()
                    Glide.with(this@MovieDetailsFragment)
                        .load(AppConstants.IMAGE_ENDPOINT_PREFIX + searchResult?.posterPath)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(binding.imageViewCover)

                    val genres = searchResult?.genres
                    binding.movieGenre.text =
                        if (genres?.isNotEmpty() == true) genres.first().name else "No Genre"
                    binding.movieReleaseDate.text = searchResult?.releaseDate
                    val voteAverage = searchResult?.voteAverage.toString()
                    val voteCount = searchResult?.voteCount.toString()
                    binding.movieRating.text = getString(R.string.rating, voteAverage, voteCount)

                    binding.movieTitle.text = searchResult?.title
                    binding.movieDescription.text = searchResult?.overview ?: "No description"
                }

                override fun onFailure(call: Call<MovieDetailsResults>, t: Throwable) {
                    Log.e("EEA TECH INTERVIEW ::", t.localizedMessage.orEmpty())
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}