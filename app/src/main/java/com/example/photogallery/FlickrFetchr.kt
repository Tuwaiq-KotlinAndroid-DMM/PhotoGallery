package com.example.photogallery

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.photogallery.api.FlickerResponse
import com.example.photogallery.api.FlickrApi
import com.example.photogallery.api.PhotoResponse
import com.example.photogallery.model.GalleryItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "FlickrFetchr"
private const val BASE_URL = "https://www.flickr.com"

class FlickrFetchr {

    // Declaration of flickerApi
    private val flickerApi: FlickrApi

    // Initialization
    init {

        // Creating an object to call the API with Retrofit
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        flickerApi = retrofit.create(FlickrApi::class.java)
    }

    // Function to fetch the photos
    fun fetchPhotos(): LiveData<List<GalleryItem>> {

        // Declaration and initialization of responseLiveData
        val responseLiveData: MutableLiveData<List<GalleryItem>> = MutableLiveData()

        // Declaration and initialization of flickerRequest
        val flickerRequest: Call<FlickerResponse> = flickerApi.fetchPhotos()



        /* enqueue(Callback<T> callback) Asynchronously send the request and
        notify callback of its response or if an error occurred talking to
       the server, creating the request, or processing the response. Response<T>
       */
        flickerRequest.enqueue(object : Callback<FlickerResponse> {

            // Handle the response from the API
            override fun onResponse(
                call: Call<FlickerResponse>,
                response: Response<FlickerResponse>
            ) {
                Log.d(TAG, "Response Recevied")

                // Get the response from response.body()
                val flickerResponse: FlickerResponse? = response.body()

                // Access photos from flickerResponse
                val photoResponse: PhotoResponse? = flickerResponse?.photos

                // Access galleryItems from photoResponse
                var galleryItems: List<GalleryItem>? = photoResponse?.galleryItems
                responseLiveData.value = galleryItems
            }

            // Handle, if the API call is not executed
            override fun onFailure(call: Call<FlickerResponse>, t: Throwable) {
                Log.d(TAG, "Failed to fetch the photos", t)
            }
        })

        // Return the live data back to the caller
        return responseLiveData
    }


    //    Denotes that the annotated method should only
    //    be called on a worker thread.
    @WorkerThread
    // Function to fetch the photo
    fun fetchPhoto(url: String): Bitmap? {

        // Execute fetchUrlBytes function from flickerApi and save the response back
        // into the response variable
        val response: Response<ResponseBody> = flickerApi.fetchUrlBytes(url).execute()

        // Generating the Bitmap
        val bitmap = response.body()?.byteStream().use(BitmapFactory::decodeStream)

        // Logging the details
        Log.d(TAG, "Decoded bitmap = $bitmap from response = $response")

        // Return bitmap to the caller
        return bitmap
    }


}