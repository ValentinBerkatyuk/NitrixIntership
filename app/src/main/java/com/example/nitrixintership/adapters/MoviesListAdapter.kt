package com.example.nitrixintership.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nitrixintership.databinding.ListmoviesRowBinding
import com.example.nitrixintership.model.MovieResult
import com.example.nitrixintership.model.Result
import com.example.nitrixintership.utills.MovieDiffUtil

class MoviesListAdapter : RecyclerView.Adapter<MoviesListAdapter.MyViewHolder>() {

    private var movies = emptyList<Result>()

    class MyViewHolder(val binding: ListmoviesRowBinding) : RecyclerView.ViewHolder(binding.root) {
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
        holder.binding.apply {
            val item = movies[position]
            Glide.with(holder.itemView.context)
                .load(item.thumb)
                .into(imageViewRow)
            textViewTitle.text = item.title
            textViewSubtitle.text = item.subtitle
            textViewDescription.text = item.description
            rowCardView.setOnClickListener {
            }
        }
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