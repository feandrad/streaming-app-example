package com.outcomehealth.app.ui.gallery

import android.graphics.Bitmap

data class VideoViewData (
    val title:String,
    val thumbnail : Bitmap?,
    val duration : Long = 0
)