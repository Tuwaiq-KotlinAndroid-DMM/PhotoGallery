package com.example.photogallery.adapter

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.photogallery.R
import com.example.photogallery.ThumbnailDownloader
import com.example.photogallery.model.GalleryItem

class PhotoAdapter(private val list: List<GalleryItem>,
val thumbnailDownloader: ThumbnailDownloader<PhotoHolder>) :
    RecyclerView.Adapter<PhotoAdapter.PhotoHolder>() {

    // onCreateViewHolder (one change here is that we are returning as an ImageView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoAdapter.PhotoHolder {
        return PhotoHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout,
                parent,
                false
            ) as ImageView
        )
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val item = list[position]

        // Placeholder for displaying the image in the
        // background until original image is loadded from the API
        val placeholder: Drawable = ContextCompat.getDrawable(
            holder.itemView.context,
            R.drawable.ic_launcher_foreground
        ) ?: ColorDrawable()

        // Bind placeholder
        holder.bindDrawable(placeholder)

        // Call thumbnailDownloader.queueThumbnail
        // to queue the url into the message queue
        thumbnailDownloader.queueThumbnail(holder, item.url)

    }

    override fun getItemCount(): Int {
        // Size of list
        return list.size
    }

    class PhotoHolder(itemView: ImageView) : RecyclerView.ViewHolder(itemView) {
        val bindDrawable: (Drawable) -> Unit = itemView::setImageDrawable
    }
}