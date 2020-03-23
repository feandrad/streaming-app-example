package com.outcomehealth.data

import android.content.Context
import com.outcomehealth.data.api.VideoApi
import com.outcomehealth.lib.VideoOH


class VideoRepository(
    private val androidContext: Context,
    private val videoApi: VideoApi
) {

    private val videos = mutableListOf<VideoOH>()
    private val metadatas = hashMapOf<String, VideoMetadata>()


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

    fun loadNextVideo(title: String): VideoOH? {
        videos.forEachIndexed { i, it ->
            if (it.title == title && i < videos.size - 1) {
                return videos[i + 1]
            }
        }
        return null
    }

    fun loadMetadata(video: VideoOH): VideoMetadata? = metadatas[video.title]

    fun saveMetadata(video: VideoOH, videoMetadata: VideoMetadata) {
        metadatas[video.title] = videoMetadata
    }

}