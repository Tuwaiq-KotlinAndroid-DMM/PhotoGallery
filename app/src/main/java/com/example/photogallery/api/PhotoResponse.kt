package com.example.photogallery.api

import com.example.photogallery.model.GalleryItem
import com.google.gson.annotations.SerializedName

class PhotoResponse {
   @SerializedName("photo")
   // Declaration for the galleryItem
   lateinit var galleryItems: List<GalleryItem>
}