package com.outcomehealth.app.usecase

import com.outcomehealth.data.VideoRepository
import com.outcomehealth.lib.VideoOH

class LoadVideoByTitleUseCase (
    private val videoRepository: VideoRepository
) {
      operator fun invoke(title: String): VideoOH? = videoRepository.loadVideoById(title)
}