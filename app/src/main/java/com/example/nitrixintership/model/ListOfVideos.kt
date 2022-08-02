package com.example.nitrixintership.model

import com.google.gson.annotations.SerializedName

class ListOfVideos(
    @SerializedName("result")
    val result: List<VideosModel>
)