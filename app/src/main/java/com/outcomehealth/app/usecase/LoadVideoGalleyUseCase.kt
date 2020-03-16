package com.outcomehealth.app.usecase

import com.outcomehealth.data.VideoRepository
import com.outcomehealth.lib.VideoOH

class LoadVideoGalleyUseCase(
private val videoRepository: VideoRepository
) {
     operator fun invoke(): List<VideoOH> = videoRepository.loadVideoGallery()
}