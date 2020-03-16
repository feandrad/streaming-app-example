package com.outcomehealth.app.ui.gallery

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.outcomehealth.app.R
import com.outcomehealth.app.ui.inflate
import com.outcomehealth.lib.VideoOH

class GalleryAdapter : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    private var elements: List<VideoOH> = listOf()
    private var filteredElements: List<VideoOH> = listOf()

    var onVideoOHClicked: (VideoOH) -> Unit = {}


    override fun getItemCount(): Int = filteredElements.size
    override fun getItemViewType(position: Int): Int = R.layout.item_gallery_video

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(viewType))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(filteredElements[position], position, onVideoOHClicked)

    fun setData(newElements: List<VideoOH>?) {
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


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            video: VideoOH,
            position: Int,
            onVideoOHClicked: (VideoOH) -> Unit
        ) {
            itemView.apply {
                setOnClickListener { onVideoOHClicked(video) }
            }
        }
    }
}