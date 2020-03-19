package com.outcomehealth.app.usecase

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import com.outcomehealth.app.ui.gallery.VideoViewData
import com.outcomehealth.data.VideoRepository
import com.outcomehealth.lib.VideoOH

class LoadVideoGalleyUseCase(
    private val videoRepository: VideoRepository
) {
    suspend operator fun invoke(): List<VideoViewData> {
        val result = mutableListOf<VideoViewData>()
        val loadVideoGallery = videoRepository.loadVideoGallery()

        loadVideoGallery.forEach {
            result.add(mapVideoViewData(it))
        }

        return result
    }

    private fun mapVideoViewData(video: VideoOH): VideoViewData {
        var thumbnail: Bitmap? = null
        var duration = 0L

        var mediaMetadataRetriever: MediaMetadataRetriever? = null
        try {
            mediaMetadataRetriever = MediaMetadataRetriever()
            mediaMetadataRetriever.setDataSource(video.url, HashMap())
//            mediaMetadataRetriever.release()
//            return mediaMetadataRetriever.embeddedPicture
            thumbnail = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST)
            duration = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toLong()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            mediaMetadataRetriever?.release()
        }

        return VideoViewData(
            title = video.title,
            thumbnail = thumbnail,
            duration = duration
        )
    }
}