package com.example.nitrixintership.repository

import com.example.nitrixintership.repository.localdata.MovieDao
import com.example.nitrixintership.repository.localdata.MovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao
) {
    fun readDatabase(): Flow<List<MovieEntity>> {
        return movieDao.readMovies()
    }

    suspend fun insertMovies(movieEntity: MovieEntity) {
        movieDao.insertMovies(movieEntity)
    }
}