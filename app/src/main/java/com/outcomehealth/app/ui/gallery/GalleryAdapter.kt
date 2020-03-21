package com.outcomehealth.app.ui.gallery

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.outcomehealth.app.R
import com.outcomehealth.app.ui.inflate
import kotlinx.android.synthetic.main.item_gallery_video.view.*
import java.util.concurrent.TimeUnit

class GalleryAdapter : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    private var elements: List<VideoViewData> = listOf()
    private var filteredElements: List<VideoViewData> = listOf()

    var onVideoOHClicked: (VideoViewData) -> Unit = {}


    override fun getItemCount(): Int = filteredElements.size
    override fun getItemViewType(position: Int): Int = R.layout.item_gallery_video

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(viewType))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(filteredElements[position], onVideoOHClicked)

    fun setData(newElements: List<VideoViewData>?) {
        elements = newElements?.sortedBy { it.title } ?: listOf()
        removeFilter()
        notifyDataSetChanged()
    }

    fun setFilter(query: String?) {
        if (query == null || query.trim() == "") {
            removeFilter()
            return
        }

        filteredElements = elements.filter {
            it.title.contains(query, true)
        }

        notifyDataSetChanged()
    }

    private fun removeFilter() {
        filteredElements = elements.toMutableList()
    }

    fun isEmpty() = getItemCount() == 0


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            video: VideoViewData,
            onVideoOHClicked: (VideoViewData) -> Unit
        ) {
            itemView.apply {
                tv_video_title.text = video.title
                tv_video_duration.text = video.duration.toDurationString()
                if (video.thumbnail != null) {
                    img_movie_poster.setImageBitmap(video.thumbnail)
                } else {
                    img_movie_poster.setImageResource(R.drawable.ic_movie)
                }
                setOnClickListener { onVideoOHClicked(video) }
            }
        }
    }
}

private fun Long.toDurationString(): String {
    val min = TimeUnit.MILLISECONDS.toMinutes(this)
    val sec = TimeUnit.MILLISECONDS.toSeconds(this) - TimeUnit.MINUTES.toSeconds(min)
    return String.format("%d:%02d", min, sec);
}
