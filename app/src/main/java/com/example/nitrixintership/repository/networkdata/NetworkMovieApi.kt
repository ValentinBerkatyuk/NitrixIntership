package com.example.nitrixintership.repository.networkdata

import com.example.nitrixintership.model.ListOfVideos
import com.example.nitrixintership.model.MovieResult
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface NetworkMovieApi {

    @GET("/getMovie")
    suspend fun getMovies(): Response<MovieResult>

    @GET("/getVideo")
    fun getVideos(): Call<ListOfVideos>
}