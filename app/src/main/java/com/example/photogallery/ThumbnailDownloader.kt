package com.example.photogallery

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.util.concurrent.ConcurrentHashMap

private const val TAG = "ThumbnailDownloader"
private const val MESSAGE_DOWNLOAD = 0

class ThumbnailDownloader<T>(
    private val responseHandler: Handler,
    private val onThumbnailDownloaded: (T, Bitmap) -> Unit
) :
    HandlerThread(TAG) {

    // Declaration of requestHandler
    private lateinit var requestHandler: Handler

    // Declaration and Initialization of requestMap as a Message Queue
    private val requestMap = ConcurrentHashMap<T, String>()

    // Declaration and Initialization of flickrFetcher
    private val flickrFetchr: FlickrFetchr = FlickrFetchr()



    // Declaration and Initialization of fragmentLifecycleObserver
    // which is of type LifecycleObserver
    val fragmentLifecycleObserver: LifecycleObserver = object : LifecycleObserver {

        // OnLifecycleEvent Annotation
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun setup() {
            Log.d(TAG, "Starting the background Process")

            // Start the looper
            start()
            looper
        }
    }

    // @SuppressLint("HandlerLeak")
    // Handler is often used in projects.
    // When you use it, you will get a yellow warning when you use it directly.
    // (Handler may have memory leaks. It is recommended that you use
    // In order to avoid warnings, we use @SuppressLint("HandlerLeak")
    @SuppressLint("HandlerLeak")
    override fun onLooperPrepared() {
        super.onLooperPrepared()
        requestHandler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

                // CHeck if "what" in msg object is equal to MESSAGE_DOWNLOAD
                if (msg.what == MESSAGE_DOWNLOAD) {

                    // Store the URL information into target
                    val target = msg.obj as T
                    Log.d(TAG, "Got a request URL: ${requestMap[target]}")

                    // Call the Handler from each and every request message in the queue
                    handleRequest(target)
                }
            }
        }
    }

    private fun handleRequest(target: T) {

        // Get the requestMap[target] and save it in url
        val url = requestMap[target] ?: return

        // Get the bitmap and save it in the variable
        val bitmap = flickrFetchr.fetchPhoto(url) ?: return


        /*
        The Runnable interface should be implemented by any class whose instances are intended
        to be executed by a thread. The class must define a method of no arguments called run.
         */

        /*
        * This interface is designed to provide a common protocol
        * for objects that wish to execute code while they are active.
        * For example, Runnable is implemented by class Thread.
        * Being active simply means that a thread has been started and has
        * not yet been stopped.*/
        responseHandler.post(Runnable {
            if (requestMap[target] != url) {
                return@Runnable
            }
            onThumbnailDownloaded(target, bitmap)
        })
    }

    // Creating a message queue
    fun queueThumbnail(target: T, url: String) {
        Log.d(TAG, "Got a URL: $url")

        // Assign url to message queue
        requestMap[target] = url

        // Obtaining a message
        requestHandler.obtainMessage(MESSAGE_DOWNLOAD, target)
            .sendToTarget()
    }


}