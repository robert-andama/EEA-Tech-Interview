package com.engie.eea_tech_interview.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.engie.eea_tech_interview.R
import com.engie.eea_tech_interview.databinding.FragmentMovieDetailsBinding
import com.engie.eea_tech_interview.model.MovieDetailsResults
import com.engie.eea_tech_interview.network.Resource
import com.engie.eea_tech_interview.network.Status
import com.engie.eea_tech_interview.utils.AppConstants
import com.engie.eea_tech_interview.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieDetailsFragment : Fragment() {

    private val movieViewModel: MovieViewModel by viewModel()
    private lateinit var binding: FragmentMovieDetailsBinding

    private val observer = Observer<Resource<MovieDetailsResults>> {
        when (it.status) {
            Status.SUCCESS -> updateMovieDetails(it)
            Status.ERROR -> showError(it.message)
            Status.LOADING -> showLoading()
        }
    }

    private fun updateMovieDetails(it: Resource<MovieDetailsResults>) {
        binding.detailsLoader.visibility = View.GONE
        Glide.with(this@MovieDetailsFragment)
            .load(AppConstants.IMAGE_ENDPOINT_PREFIX + it.data?.posterPath)
            .error(R.drawable.ic_launcher_foreground)
            .into(binding.imageViewCover)

        val genres = it.data?.genres
        binding.movieGenre.text = if (genres?.isNotEmpty() == true) genres.first().name else "No Genre"
        binding.movieReleaseDate.text = it.data?.releaseDate
        val voteAverage = it.data?.voteAverage.toString()
        val voteCount = it.data?.voteCount.toString()
        binding.movieRating.text = getString(R.string.rating, voteAverage, voteCount)

        binding.movieTitle.text = it.data?.title
        binding.movieDescription.text = it.data?.overview ?: "No description"
    }

    private fun showError(empty: String?) {
        binding.detailsLoader.visibility = View.GONE
        Toast.makeText(requireActivity(), empty, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        binding.detailsLoader.visibility = View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        val movieID: Int? = arguments?.getInt("movie", 0)
        movieViewModel.getMovieId(movieID.toString())
        observeMovieQuery()
        return binding.root
    }

    private fun observeMovieQuery() {
        movieViewModel.movieDetails.observe(viewLifecycleOwner, observer)
    }
}