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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf


class MediaPlayerControllerImpl(context: Context) : MediaPlayerController {
    private val player = ExoPlayer.Builder(context).build()

    override fun prepare(listener: MediaPlayerListener) {
        player.addListener(object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                listener.onError()
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                super.onMediaItemTransition(mediaItem, reason)
                when (reason) {
                    Player.MEDIA_ITEM_TRANSITION_REASON_AUTO -> {
                        listener.onAudioCompleted()
                        Log.d("media", "completed")
                    }

                    Player.MEDIA_ITEM_TRANSITION_REASON_SEEK -> {

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
        player.prepare()
    }

    override fun start(pathSources: List<String>) {
        val mediaItem = pathSources.map { MediaItem.fromUri(it) }
        player.clearMediaItems()
        player.setMediaItems(mediaItem)
        Log.d("media", "player.start")
    }

    override fun pause() {
        if (player.isPlaying) player.pause() else player.play()
    }

    override fun stop() {
        player.stop()
    }

    override fun playByIndex(index: Int) {
        player.seekTo(index, 0L)
        player.play()
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

    override fun playNext() {
        player.seekToNext()
    }

    override fun playPrevious() {
        player.seekToPreviousMediaItem()
    }

    override fun getDuration(): Long {
        return player.duration
    }

    override fun playbackTimeFlow(): Flow<Long> {
        return flow {
            while (true) {
                emit(player.currentPosition)
                delay(1000)
            }
        }
    }

    override fun seekToTime(time: Long) {
        player.seekTo(time)
    }


}