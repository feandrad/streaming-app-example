package com.outcomehealth.app.ui.player

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.outcomehealth.app.R
import com.outcomehealth.app.usecase.LoadVideoByTitleUseCase
import com.outcomehealth.lib.PlayerConfig
import com.outcomehealth.lib.VideoOH


class PlayerViewModel(
    private val loadVideoByTitle: LoadVideoByTitleUseCase
) : ViewModel() {


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

        val mediaSource = extractorMediaSource(context, videoLiveData.value?.url)

        val player = SimpleExoPlayer.Builder(context).build()

        player.prepare(mediaSource)
        player.playWhenReady = true
        playerConfigLiveData.value?.let {
            player.seekTo(it.currentWindow, it.playbackPosition)
        }
        playerLiveData.value = player
    }

    private fun extractorMediaSource(context: Context, url: String?): ProgressiveMediaSource {
        val userAgent = Util.getUserAgent(context, context.getString(R.string.app_name))
        return ProgressiveMediaSource.Factory(DefaultDataSourceFactory(context, userAgent))
            .createMediaSource(Uri.parse(url))
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