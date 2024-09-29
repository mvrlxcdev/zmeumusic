package dev.mvrlxc.zmeumusic.domain.player

interface MediaPlayerListener {
    fun onReady()
    fun onAudioCompleted()
    fun onError()
}