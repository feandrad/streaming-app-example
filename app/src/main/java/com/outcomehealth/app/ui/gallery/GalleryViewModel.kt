package com.outcomehealth.app.ui.gallery

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.outcomehealth.app.ui.base.BaseViewModel
import com.outcomehealth.app.usecase.LoadVideoGalleyUseCase
import com.outcomehealth.lib.VideoOH

class GalleryViewModel(
    private val loadVideoGallery: LoadVideoGalleyUseCase
) : BaseViewModel() {

    val videoList = MutableLiveData<List<VideoOH>>()
    val selectedVideo = MutableLiveData<VideoOH>()

    override fun activityCreated(bundle: Bundle?) {
        videoList.value = loadVideoGallery()
    }


    fun videoClicked(video: VideoOH) {
        selectedVideo.value = video
    }

}