package dev.mvrlxc.zmeumusic.domain.player

import android.content.Context

interface MediaPlayerController {
    fun prepare(pathSource: String, listener: MediaPlayerListener)

    fun start()

    fun pause()

    fun stop()

    fun isPlaying(): Boolean

    fun release()

    fun addTrackToQueue(pathSource: String)

    fun playNext()

    fun playPrevious()
}