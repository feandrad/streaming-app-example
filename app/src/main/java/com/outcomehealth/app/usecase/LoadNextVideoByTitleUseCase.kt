package com.outcomehealth.app.usecase

import com.outcomehealth.data.VideoRepository
import com.outcomehealth.lib.VideoOH

class LoadNextVideoByTitleUseCase(
    private val videoRepository: VideoRepository
) {
    operator fun invoke(title: String): VideoOH? = videoRepository.loadNextVideo(title)
}