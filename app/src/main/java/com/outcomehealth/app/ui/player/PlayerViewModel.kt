package com.outcomehealth.app.ui.player

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.outcomehealth.app.R
import com.outcomehealth.app.ui.base.BaseViewModel
import com.outcomehealth.app.ui.gallery.PlayingVideo
import com.outcomehealth.app.ui.gallery.VideoViewData
import com.outcomehealth.app.usecase.LoadNextVideoByTitleUseCase
import com.outcomehealth.app.usecase.LoadVideoByTitleUseCase
import com.outcomehealth.app.usecase.LoadVideoGalleyUseCase
import com.outcomehealth.lib.PlayerConfig
import kotlinx.coroutines.Dispatchers


class PlayerViewModel(
    private val loadVideoByTitle: LoadVideoByTitleUseCase,
    private val loadNextVideoByTitle: LoadNextVideoByTitleUseCase,
    private val loadVideoGalley: LoadVideoGalleyUseCase
) : BaseViewModel() {

    val videoLiveData = MutableLiveData<PlayingVideo>()
    val playerConfigLiveData = MutableLiveData<PlayerConfig>()
    val playerLiveData = MutableLiveData<SimpleExoPlayer>()

    val videoList: LiveData<List<VideoViewData>> = liveData(Dispatchers.IO) {
        val retrievedData = loadVideoGalley()
        emit(retrievedData)
    }

    override fun activityCreated(bundle: Bundle?) {
        bundle?.getString(PlayerActivity.SELECTED_VIDEO)?.let {
            videoLiveData.value = loadVideoByTitle(it)
        }
    }

    override fun activityResumed(context: Context) {
        initializePlayer(context)
    }

    override fun activityPaused(context: Context) {
        releasePlayer()
    }

    private fun initializePlayer(context: Context) {
        val player = SimpleExoPlayer.Builder(context).build()
        playVideo(context, player)
    }

    private fun playVideo(context: Context, player: SimpleExoPlayer) {
        val mediaSource = extractorMediaSource(context, videoLiveData.value?.url)
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

    fun videoClicked(video: VideoViewData, context: Context) {
        videoLiveData.value = loadVideoByTitle(video.title)
        playVideo(context, playerLiveData.value!!)
    }
}