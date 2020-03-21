package com.outcomehealth.app.usecase

import com.outcomehealth.app.ui.gallery.PlayingVideo
import com.outcomehealth.data.VideoRepository

class LoadNextVideoByTitleUseCase(
    private val videoRepository: VideoRepository
) {
    operator fun invoke(title: String): PlayingVideo? {
        val videoOH = videoRepository.loadNextVideo(title) ?: return null
        return PlayingVideo(
            title = videoOH.title,
            url = videoOH.url
        )

    }
}