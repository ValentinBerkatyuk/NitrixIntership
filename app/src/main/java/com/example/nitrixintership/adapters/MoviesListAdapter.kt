package com.example.nitrixintership.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nitrixintership.databinding.ListmoviesRowBinding
import com.example.nitrixintership.model.MovieResult
import com.example.nitrixintership.model.Result
import com.example.nitrixintership.utills.MovieDiffUtil

class MoviesListAdapter : RecyclerView.Adapter<MoviesListAdapter.MyViewHolder>() {

    private var movies = emptyList<Result>()

    class MyViewHolder(private val binding: ListmoviesRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Result) {
            binding.movies = data
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListmoviesRowBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentMovie = movies[position]
        holder.bind(currentMovie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun setData(newData: MovieResult) {
        val movieDiffUtil = MovieDiffUtil(movies, newData.result)
        val movieUtilResult = DiffUtil.calculateDiff(movieDiffUtil)
        movies = newData.result
        movieUtilResult.dispatchUpdatesTo(this)
    }
}