package com.example.nitrixintership.repository.localdata

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nitrixintership.model.MovieResult
import com.example.nitrixintership.utills.Constants.Companion.MOVIE_TABLE

@Entity(tableName = MOVIE_TABLE)
class MovieEntity(
    var movieResult: MovieResult
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}