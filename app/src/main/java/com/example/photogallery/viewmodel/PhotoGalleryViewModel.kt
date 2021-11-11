package com.example.photogallery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.photogallery.FlickrFetchr
import com.example.photogallery.model.GalleryItem

class PhotoGalleryViewModel : ViewModel() {

    // Declaration for the galleryItemLiveData
    // Live Data with List of Gallery Item

    // Why Live Data
    // LiveData is an observable data holder class. Unlike a regular observable,
    // LiveData is lifecycle-aware, meaning it respects the lifecycle of other app
    // components, such as activities, fragments, or services.
    // This awareness ensures LiveData only updates app component observers
    // that are in an active lifecycle state.


    // The advantages of using LiveData:
    // Ensures your UI matches your data state
    // No memory leaks
    // No crashes due to stopped activities
    // No more manual lifecycle handling
    // Always up to date data
    // Proper configuration changes
    // Sharing resources
    val galleryItemLiveData: LiveData<List<GalleryItem>>


    // Initialization
    init {
        galleryItemLiveData = FlickrFetchr().fetchPhotos()
    }

}