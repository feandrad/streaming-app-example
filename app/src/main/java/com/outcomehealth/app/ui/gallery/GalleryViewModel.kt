package com.outcomehealth.app.ui.gallery

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.outcomehealth.app.usecase.LoadVideoGalleyUseCase
import com.outcomehealth.lib.VideoOH

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