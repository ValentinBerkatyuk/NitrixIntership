package com.example.nitrixintership.model


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("description")
    val description: String,
    @SerializedName("sourcevideo")
    val sourcevideo: String,
    @SerializedName("subtitle")
    val subtitle: String,
    @SerializedName("thumb")
    val thumb: String,
    @SerializedName("title")
    val title: String
)