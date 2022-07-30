package com.example.nitrixintership.repository.localdata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(moviesEntity: MovieEntity)

    @Query("SELECT * FROM movies_table ORDER BY id ASC")
    fun readMovies(): Flow<List<MovieEntity>>

}