package com.outcomehealth.lib

data class PlayerConfig(
    val playbackPosition: Long,
    val currentWindow: Int,
    val playWhenReady: Boolean
)