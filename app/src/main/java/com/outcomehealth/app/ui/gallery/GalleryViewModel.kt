package com.outcomehealth.app.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.outcomehealth.app.ui.base.BaseViewModel
import com.outcomehealth.app.usecase.LoadVideoGalleyUseCase
import com.outcomehealth.lib.VideoOH
import kotlinx.coroutines.Dispatchers

class GalleryViewModel(
    private val loadVideoGallery: LoadVideoGalleyUseCase
) : BaseViewModel() {

    val selectedVideo = MutableLiveData<VideoOH>()

    val videoList: LiveData<List<VideoOH>> = liveData(Dispatchers.IO) {
        val retrievedData = loadVideoGallery()
        emit(retrievedData)
    }


    fun videoClicked(video: VideoOH) {
        selectedVideo.value = video
    }

}