package com.example.nitrixintership.model


import com.google.gson.annotations.SerializedName

data class MovieResult(
    @SerializedName("result")
    val result: List<Result>
)