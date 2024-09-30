package dev.mvrlxc.zmeumusic.domain.player

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface MediaPlayerController {
    fun prepare(listener: MediaPlayerListener)

    fun start(pathSources: List<String>)

    fun pause()

    fun stop()

    fun playByIndex(index: Int)

    fun isPlaying(): Boolean

    fun release()

    fun addTrackToQueue(pathSource: String)

    fun playNext()

    fun playPrevious()

    fun getDuration(): Long

    fun playbackTimeFlow(): Flow<Long>

    fun seekToTime(time: Long)

}