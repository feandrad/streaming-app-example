package com.outcomehealth.app.usecase

import com.outcomehealth.app.ui.gallery.PlayingVideo
import com.outcomehealth.app.ui.gallery.VideoViewData
import com.outcomehealth.data.VideoRepository
import com.outcomehealth.lib.VideoOH

class LoadVideoByTitleUseCase(
    private val videoRepository: VideoRepository
) {
    operator fun invoke(title: String): PlayingVideo? {
        val videoOH = videoRepository.loadVideoById(title) ?: return null
        return PlayingVideo(
            title = videoOH.title,
            url = videoOH.url
        )
    }
}