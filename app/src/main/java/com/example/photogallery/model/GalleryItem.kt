package com.example.photogallery.model


import com.google.gson.annotations.SerializedName

data class GalleryItem(
    @SerializedName("farm")
    val farm: Int,
    @SerializedName("height_s")
    val height: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("isfamily")
    val isfamily: Int,
    @SerializedName("isfriend")
    val isfriend: Int,
    @SerializedName("ispublic")
    val ispublic: Int,
    @SerializedName("owner")
    val owner: String,
    @SerializedName("secret")
    val secret: String,
    @SerializedName("server")
    val server: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("url_s")
    val url: String,
    @SerializedName("width_s")
    val width: Int
)