package com.example.nitrixintership.repository.localdata

import androidx.room.TypeConverter
import com.example.nitrixintership.model.MovieResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MovieTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun gsonObjectToString(movieResult: MovieResult): String {
        return gson.toJson(movieResult)
    }

    @TypeConverter
    fun stringToGsonObject(data: String): MovieResult {
        val listType = object : TypeToken<MovieResult>() {}.type
        return gson.fromJson(data, listType)
    }

}