package com.example.nitrixintership.repository.networkdata

import com.example.nitrixintership.model.ListOfVideos
import com.example.nitrixintership.model.MovieResult
import retrofit2.Response
import retrofit2.http.GET

interface NetworkMovieApi {

    @GET("/getMovies")
    suspend fun getMovies(): Response<MovieResult>

    @GET("/getVideos")
    suspend fun getVideos(): Response<ListOfVideos>
}