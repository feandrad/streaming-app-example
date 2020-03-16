package com.outcomehealth.app.usecase

import com.outcomehealth.data.VideoRepository
import com.outcomehealth.lib.VideoOH

class LoadMovieGalleyUseCase(
    private val videoRepository: VideoRepository
) {
    suspend operator fun invoke(): List<VideoOH> = videoRepository.loadMovieGallery()
}