package dev.mvrlxc.zmeumusic.data.player

import android.content.Context
import android.util.Log
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Player.STATE_READY
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import dev.mvrlxc.zmeumusic.domain.player.MediaPlayerController
import dev.mvrlxc.zmeumusic.domain.player.MediaPlayerListener

class MediaPlayerControllerImpl (context: Context) : MediaPlayerController {
    private val player = ExoPlayer.Builder(context).build()

    override fun prepare(pathSource: String, listener: MediaPlayerListener) {
        val mediaItem = listOf(MediaItem.fromUri(pathSource))

        player.addListener(object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                listener.onError()
                Log.d("media", "listener on error")
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                super.onMediaItemTransition(mediaItem, reason)
                when (reason) {
                    Player.MEDIA_ITEM_TRANSITION_REASON_AUTO -> {
                        listener.onAudioCompleted()
                        Log.d("media", "listener on audio completed")
                    }

                }
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                when (playbackState) {
                    STATE_READY -> {
                        listener.onReady()
                        Log.d("media", "listener on ready")
                    }
                }
            }

            override fun onPlayerErrorChanged(error: PlaybackException?) {
                super.onPlayerErrorChanged(error)
                listener.onError()
            }
        })
        player.clearMediaItems()
        player.setMediaItems(mediaItem)
        player.prepare()
    }

    override fun start() {
        player.play()
        Log.d("media", "player.start")
    }

    override fun pause() {
        if (player.isPlaying)
            player.pause()
    }

    override fun stop() {
        player.stop()
    }

    override fun release() {
        player.release()

    }

    override fun isPlaying(): Boolean {
        return player.isPlaying
    }

    override fun addTrackToQueue(pathSource: String) {
        player.addMediaItem(MediaItem.fromUri(pathSource))
        Log.d("media", "player.add to queue")
    }

    @OptIn(UnstableApi::class)
    override fun playNext() {
        player.next()
    }

    @OptIn(UnstableApi::class)
    override fun playPrevious() {
        player.previous()
    }
}