package com.outcomehealth.app.usecase

import com.outcomehealth.data.VideoRepository
import com.outcomehealth.lib.VideoOH

class LoadVideoByIdUseCase (
    private val videoRepository: VideoRepository
) {
      operator fun invoke(id :Int): VideoOH = videoRepository.loadVideoById(id)
}