package com.outcomehealth.app.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.outcomehealth.app.usecase.LoadMovieGalleyUseCase
import com.outcomehealth.lib.VideoOH
import kotlinx.coroutines.Dispatchers

class GalleryViewModel(
    private val loadVideoGallery: LoadMovieGalleyUseCase
) : ViewModel() {

    val videoList: LiveData<List<VideoOH>> = liveData(Dispatchers.IO) {
        val retrievedData = loadVideoGallery()
        emit(retrievedData)
    }

    val selectedVideo = MutableLiveData<VideoOH>()


    fun videoClicked(video: VideoOH) {
        selectedVideo.value = video
    }
}