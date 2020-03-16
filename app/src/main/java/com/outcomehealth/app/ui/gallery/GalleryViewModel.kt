package com.outcomehealth.app.ui.gallery

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.outcomehealth.app.usecase.LoadVideoGalleyUseCase
import com.outcomehealth.lib.VideoOH
import kotlinx.coroutines.Dispatchers

class GalleryViewModel(
    private val loadVideoGallery: LoadVideoGalleyUseCase
) : ViewModel() {

    val videoList = MutableLiveData<List<VideoOH>>()
    val selectedVideo = MutableLiveData<VideoOH>()

    fun activityCreated(bundle: Bundle?) {
        videoList.value = loadVideoGallery()
    }


    fun videoClicked(video: VideoOH) {
        selectedVideo.value = video
    }

}