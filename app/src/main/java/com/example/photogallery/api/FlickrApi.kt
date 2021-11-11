package com.example.photogallery.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface FlickrApi {

    // HTTP GET Call
    @GET("/services/rest/?method=flickr.interestingness.getList&api_key=d0e36df9484565fbc64a8982091d9da5&format=json&nojsoncallback=1&extras=url_s")

    //Function to fetch the photos
    // Call is an invocation of a Retrofit method that sends a request
    // to a webserver and returns a response
    fun fetchPhotos(): Call<FlickerResponse>

    @GET
    // Function to fetch the URL bytes
    // Call is an invocation of a Retrofit method that sends a request
    // to a webserver and returns a response
    fun fetchUrlBytes(@Url url: String): Call<ResponseBody>
}