package com.example.photogallery

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photogallery.adapter.PhotoAdapter
import com.example.photogallery.viewmodel.PhotoGalleryViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PhotoGalleryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "PhotoGalleryFragment"

class PhotoGalleryFragment : Fragment() {


    // Declaration for photoGalleryViewModel
    private lateinit var photoGalleryViewModel: PhotoGalleryViewModel

    // Declaration for photoRecyclerView
    private lateinit var photoRecyclerView: RecyclerView

    // Declaration for thumbnailDownloader
    private lateinit var thumbnailDownloader: ThumbnailDownloader<PhotoAdapter.PhotoHolder>



    // onCreate Method
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialization for photoGalleryViewModel
        photoGalleryViewModel = ViewModelProvider(this).get(PhotoGalleryViewModel::class.java)

        // Creating a response handler
        val responseHandler = Handler()

        // creating an instance of ThumbnailDownloader class
        // and storing it in an object thumbnailDownloader
        thumbnailDownloader = ThumbnailDownloader(responseHandler) { photoHolder, bitmap ->
            val drawable = BitmapDrawable(resources, bitmap)
            photoHolder.bindDrawable(drawable)
        }

        // Attaching the observer with thumbnailDownloader.fragmentLifecycleObserver
        lifecycle.addObserver(thumbnailDownloader.fragmentLifecycleObserver)
    }

    // onCreateView Method
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_photo_gallery, container, false)

        // Taking the context of photo_recycler_view
        photoRecyclerView = view.findViewById(R.id.photo_recycler_view)

        // Configuration for Grid layout
        photoRecyclerView.layoutManager = GridLayoutManager(context, 3)

        // return view
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Attaching the observer
        photoGalleryViewModel.galleryItemLiveData.observe(
            viewLifecycleOwner,
            Observer { galleryItem ->
                Log.d(TAG, "Gallery Item")

                // Calling Adapter with galleryItem and thumbnailDownloader
                photoRecyclerView.adapter = PhotoAdapter(galleryItem, thumbnailDownloader)
            }
        )

    }

    companion object {
        // Creating new instance of the PhotoGalleryFragment
        fun newinstance() = PhotoGalleryFragment()
    }

}