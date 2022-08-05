package com.example.nitrixintership.model

import com.google.gson.annotations.SerializedName

data class VideosModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("video")
    val video: String
)