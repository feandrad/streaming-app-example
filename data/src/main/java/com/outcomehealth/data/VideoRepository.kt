package com.outcomehealth.data

import android.content.Context
import com.outcomehealth.data.api.VideoApi
import com.outcomehealth.lib.VideoOH


class VideoRepository(
    private val androidContext: Context,
    private val videoApi: VideoApi
) {

    private val videos = mutableListOf<VideoOH>()


    suspend fun loadVideoGallery(): List<VideoOH> {
        videos.clear()
        val result = videoApi.loadVideoManifest()
        result.forEach {
            videos.add(
                VideoOH(
                    title = it.title,
                    url = it.url
                )
            )

        }
        return videos
    }

    fun loadVideoById(title: String): VideoOH? {
        return videos.find { it.title == title }
    }

}