package com.engie.eea_tech_interview.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.engie.eea_tech_interview.R
import com.engie.eea_tech_interview.api.MovieApiService
import com.engie.eea_tech_interview.databinding.FragmentMovieListBinding
import com.engie.eea_tech_interview.model.Movie
import com.engie.eea_tech_interview.model.SearchResult
import com.engie.eea_tech_interview.ui.adapter.MovieAdapter
import com.engie.eea_tech_interview.utils.AppConstants.MOVIE_API_KEY
import com.engie.eea_tech_interview.utils.AppConstants.SEARCH_QUERY
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private val retrofit: Retrofit by inject()
    private val movieApiService: MovieApiService = retrofit.create(MovieApiService::class.java)
    private var adapter: MovieAdapter = MovieAdapter { flower -> adapterOnClick(flower) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerviewMovies.adapter = adapter
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recyclerviewMovies.layoutManager = staggeredGridLayoutManager

        getMovies(movieApiService)
        binding.refreshMovieList.setOnRefreshListener {
            getMovies(movieApiService)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun adapterOnClick(movie: Movie) {
//        val intent = Intent(this, MovieDetails()::class.java)
//        intent.putExtra("movie", movie.id)
//        startActivity(intent)
        val bundle = Bundle()
        bundle.putInt("movie", movie.id)
        findNavController().navigate(R.id.action_MovieListFragment_to_MovieDetailsFragment, bundle)
    }

    private fun getMovies(movieApiService: MovieApiService) {
        movieApiService.getMovies(MOVIE_API_KEY, SEARCH_QUERY)
            .enqueue(object : Callback<SearchResult> {
                override fun onResponse(
                    call: Call<SearchResult>,
                    response: Response<SearchResult>,
                ) {
                    binding.refreshMovieList.isRefreshing = false
                    val searchResult = response.body()
                    adapter.dataList = searchResult!!.results

                    showNoDataFound(adapter.dataList.isEmpty())
                    println("EEA TECH INTERVIEW :: $searchResult")
                    println("EEA TECH INTERVIEW :: ${searchResult.results}")
                    Log.d("EEA TECH INTERVIEW",
                        searchResult.results?.joinToString(separator = ",").orEmpty())
                }

                override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                    Log.e("EEA TECH INTERVIEW", t.localizedMessage.orEmpty())
                }
            })
    }

    private fun showNoDataFound(show: Boolean) = if (show) {
        binding.emptyMovieState.visibility = View.VISIBLE
        binding.recyclerviewMovies.visibility = View.GONE
    } else {
        binding.emptyMovieState.visibility = View.GONE
        binding.recyclerviewMovies.visibility = View.VISIBLE
    }
}