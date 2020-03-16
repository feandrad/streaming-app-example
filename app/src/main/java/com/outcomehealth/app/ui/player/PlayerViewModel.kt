package com.outcomehealth.app.ui.player

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.outcomehealth.app.usecase.LoadVideoByTitleUseCase
import com.outcomehealth.lib.PlayerConfig
import com.outcomehealth.lib.VideoOH

class PlayerViewModel (
    private val loadVideoByTitle: LoadVideoByTitleUseCase
): ViewModel() {


    val videoLiveData = MutableLiveData<VideoOH>()
    val playerLiveData = MutableLiveData<SimpleExoPlayer>()
    val playerConfigLiveData = MutableLiveData<PlayerConfig>()


    fun activityCreated(bundle: Bundle?) {
        bundle?.getString(PlayerActivity.SELECTED_VIDEO)?.let {
            videoLiveData.value = loadVideoByTitle(it)
        }
    }

    fun activityResumed(context: Context) {
        initializePlayer(context)
    }

    fun activityPaused(context: Context) {
        releasePlayer()
    }


    private fun initializePlayer(context: Context) {
        playerConfigLiveData.value?.let {
            val player = ExoPlayerFactory.newSimpleInstance(
                DefaultRenderersFactory(context),
                DefaultTrackSelector(),
                DefaultLoadControl()
            )
            player.playWhenReady = it.playWhenReady
            player.seekTo(it.currentWindow, it.playbackPosition)

//            val mediaSource = buildMediaSource(Uri.parse(videoLiveData.value?.uri ?: ""))
//            player.prepare(mediaSource, true, false)

            playerLiveData.value = player
        }
    }

    private fun releasePlayer() {
        playerLiveData.value?.let {
            playerConfigLiveData.value = PlayerConfig(
                playbackPosition = it.currentPosition,
                currentWindow = it.currentWindowIndex,
                playWhenReady = it.playWhenReady
            )
            it.release()
        }
    }
}