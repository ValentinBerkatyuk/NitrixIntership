package com.example.nitrixintership.repository.networkdata

import com.example.nitrixintership.model.MovieResult
import retrofit2.Response

class RemoteDataSource constructor(
    private val networkMovieApi: NetworkMovieApi
) {
    suspend fun getMovies(): Response<MovieResult>{
        return networkMovieApi.getMovies()
    }
}