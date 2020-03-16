package com.outcomehealth.data

import com.outcomehealth.lib.VideoOH

class VideoRepository {

    private val videos = mutableListOf<VideoOH>()


    fun loadVideoGallery(): List<VideoOH> {
        val result = listOf(
            VideoOH(
                id = 0,
                title = "Mock Title",
                url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
            )
        )

        videos.clear()
        videos.addAll(result)

        return videos
    }

    fun loadVideoById(id: Int): VideoOH {
        return videos[id]
    }
}