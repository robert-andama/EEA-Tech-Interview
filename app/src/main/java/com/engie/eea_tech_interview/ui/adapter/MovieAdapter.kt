package com.engie.eea_tech_interview.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.engie.eea_tech_interview.R
import com.engie.eea_tech_interview.databinding.RowMovieBinding
import com.engie.eea_tech_interview.model.Movie
import com.engie.eea_tech_interview.utils.AppConstants


class MovieAdapter(private val clickListener: (Movie) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var dataList: List<Movie> = emptyList<Movie>().toMutableList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowMovieBinding.inflate(inflater, parent, false)
        return RecyclerViewAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RecyclerViewAdapterViewHolder).bind(dataList[position], clickListener)
    }

    override fun getItemCount(): Int = dataList.size

    inner class RecyclerViewAdapterViewHolder(private val binding: RowMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, clickListener: (Movie) -> Unit) {
            loadImageUrl(binding.imageViewCover, movie.posterPath)
            binding.movieName.text = movie.title
            binding.movieReleaseDate.text = movie.releaseDate
            binding.root.setOnClickListener { clickListener(movie) }
        }
    }

    private fun loadImageUrl(view: AppCompatImageView, url: String?) {
            Glide.with(view.context)
                .load(AppConstants.IMAGE_ENDPOINT_PREFIX + url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(view)
    }

}