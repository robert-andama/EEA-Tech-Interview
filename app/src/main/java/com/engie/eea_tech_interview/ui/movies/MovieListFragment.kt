package com.engie.eea_tech_interview.ui.movies

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.engie.eea_tech_interview.R
import com.engie.eea_tech_interview.databinding.FragmentMovieListBinding
import com.engie.eea_tech_interview.model.Movie
import com.engie.eea_tech_interview.model.SearchResult
import com.engie.eea_tech_interview.network.Resource
import com.engie.eea_tech_interview.network.Status
import com.engie.eea_tech_interview.ui.adapter.MovieAdapter
import com.engie.eea_tech_interview.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module


class MovieListFragment : Fragment() {

    private val movieViewModel : MovieViewModel by viewModel()
    private lateinit var binding: FragmentMovieListBinding
    private var adapter: MovieAdapter = MovieAdapter { flower -> adapterOnClick(flower) }

    private val observer = Observer<Resource<SearchResult>> {
        when (it.status) {
            Status.SUCCESS -> getPopularMovies(it)
            Status.ERROR -> showNoDataFound(adapter.dataList.isEmpty())
            Status.LOADING -> showLoading(true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        showNoDataFound(true)
        setupRecyclerView()
        observeMovieQuery()
        return binding.root
    }

    private fun observeMovieQuery() {
        binding.viewModel = movieViewModel
        movieViewModel.movieQuery.observe(viewLifecycleOwner, observer)
    }

    private fun closeKeyBoard() {
        try {
            val imm: InputMethodManager? =
                requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        } catch (_: Exception) {
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerviewMovies.adapter = adapter
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recyclerviewMovies.layoutManager = staggeredGridLayoutManager
    }

    private fun adapterOnClick(movie: Movie) {
        val bundle = Bundle()
        bundle.putInt("movie", movie.id)
        findNavController().navigate(R.id.action_MovieListFragment_to_MovieDetailsFragment, bundle)
    }

    private  fun getPopularMovies(it: Resource<SearchResult>) {

        showLoading(false)
        showNoDataFound(false)
        val searchResult = it.data
        adapter.dataList = searchResult!!.results
    }

    private fun showLoading(show: Boolean) = if (show) {
        closeKeyBoard()
        binding.movieLoader.visibility = View.VISIBLE
        binding.recyclerviewMovies.visibility = View.GONE
    } else {
        closeKeyBoard()
        binding.movieLoader.visibility = View.GONE
        binding.recyclerviewMovies.visibility = View.VISIBLE
    }

    private fun showNoDataFound(show: Boolean) = if (show) {
        showLoading(false)
        binding.emptyMovieState.visibility = View.VISIBLE
        binding.recyclerviewMovies.visibility = View.GONE
    } else {
        showLoading(false)
        binding.emptyMovieState.visibility = View.GONE
        binding.recyclerviewMovies.visibility = View.VISIBLE
    }
}