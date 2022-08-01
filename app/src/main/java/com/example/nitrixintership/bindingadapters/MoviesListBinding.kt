package com.example.nitrixintership.bindingadapters

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.nitrixintership.R
import com.example.nitrixintership.fragments.MovieFragmentDirections
import com.example.nitrixintership.model.Result

class MoviesListBinding {
    companion object {

        @BindingAdapter("onMovieClickListener")
        @JvmStatic
        fun onMovieClickListener(movieRowLayout: ConstraintLayout, data: Result) =
            movieRowLayout.setOnClickListener {
                val action = MovieFragmentDirections.actionMovieFragmentToMoviePlayer(data)
                movieRowLayout.findNavController().navigate(action)
            }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        }
    }
}