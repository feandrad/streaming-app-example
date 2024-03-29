package com.outcomehealth.app.ui.player

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.google.android.exoplayer2.Player
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

    override fun makeOutOfStateBundle(outState: Bundle): Bundle {
        val bundle = Bundle()
        bundle.putString(PlayerActivity.SELECTED_VIDEO, videoLiveData.value?.url)
        return bundle
    }


    fun videoClicked(video: VideoViewData, context: Context) {
        videoLiveData.value = loadVideoByTitle(video.title)
        updatePlayerConfigForNewVideo()
        playVideo(context, playerLiveData.value!!)
    }

    fun startNextVideoIfAvailable(context: Context) {
        videoLiveData.value?.let {
            videoLiveData.value = loadNextVideoByTitle(it.title)
            updatePlayerConfigForNewVideo()
            playVideo(context, playerLiveData.value!!)
        }
    }


    private fun initializePlayer(context: Context) {
        val player = SimpleExoPlayer.Builder(context).build()
        playVideo(context, player)
    }

    private fun playVideo(context: Context, player: SimpleExoPlayer) {
        videoLiveData.value?.let { video ->
            val mediaSource = extractorMediaSource(context, video.url)
            player.prepare(mediaSource)

            player.playWhenReady = true
            playerConfigLiveData.value?.let {
                player.seekTo(it.currentWindow, it.playbackPosition)
            }

            player.addListener(initPlayerListener(context))
            playerLiveData.value = player

        } ?: releasePlayer()
    }

    private fun initPlayerListener(context: Context): Player.EventListener {
        return object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) {
                    Player.STATE_ENDED -> startNextVideoIfAvailable(context)
                    Player.STATE_BUFFERING -> {
                    }
                    Player.STATE_IDLE -> {
                    }
                    Player.STATE_READY -> {
                    }
                }
            }
        }
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

    private fun updatePlayerConfigForNewVideo() {
        playerConfigLiveData.value?.let{
            playerConfigLiveData.value = PlayerConfig(
                playbackPosition = 0L,
                currentWindow = it.currentWindow,
                playWhenReady = it.playWhenReady
            )
        }
    }
}