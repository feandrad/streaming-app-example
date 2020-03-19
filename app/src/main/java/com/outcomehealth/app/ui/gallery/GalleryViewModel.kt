package com.outcomehealth.app.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.outcomehealth.app.ui.base.BaseViewModel
import com.outcomehealth.app.usecase.LoadVideoGalleyUseCase
import kotlinx.coroutines.Dispatchers

class GalleryViewModel(
    private val loadVideoGallery: LoadVideoGalleyUseCase
) : BaseViewModel() {

    val selectedVideo = MutableLiveData<VideoViewData>()

    val videoList: LiveData<List<VideoViewData>> = liveData(Dispatchers.IO) {
        val retrievedData = loadVideoGallery()
        emit(retrievedData)
    }


    fun videoClicked(video: VideoViewData) {
        selectedVideo.value = video
    }

}