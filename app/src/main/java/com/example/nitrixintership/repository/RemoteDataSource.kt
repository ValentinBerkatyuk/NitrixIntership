package com.example.nitrixintership.repository

import com.example.nitrixintership.model.ListOfVideos
import com.example.nitrixintership.model.MovieResult
import com.example.nitrixintership.repository.networkdata.NetworkMovieApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val networkMovieApi: NetworkMovieApi
) {
    suspend fun getMovies(): Response<MovieResult> {
        return networkMovieApi.getMovies()
    }

    suspend fun getVideos(): Response<ListOfVideos> {
        return networkMovieApi.getVideos()
    }
}