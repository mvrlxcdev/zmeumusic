package dev.mvrlxc.zmeumusic.ui.screen.player

import androidx.compose.foundation.pager.PagerState
import androidx.lifecycle.viewModelScope
import dev.mvrlxc.zmeumusic.data.model.TrackData
import dev.mvrlxc.zmeumusic.data.remote.model.SearchSongsDTO
import dev.mvrlxc.zmeumusic.domain.player.MediaPlayerController
import dev.mvrlxc.zmeumusic.domain.player.MediaPlayerListener
import dev.mvrlxc.zmeumusic.ui.base.BaseViewModel
import dev.mvrlxc.zmeumusic.ui.base.ViewEvent
import dev.mvrlxc.zmeumusic.utils.getTrackLink
import dev.mvrlxc.zmeumusic.utils.millisToMinutesAndSeconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

class PlayerViewModel(
    private val mediaPlayerController: MediaPlayerController,
) : BaseViewModel<PlayerViewState, PlayerViewEvent>() {

    init {
        prepare()
    }

    override fun createInitialState(): PlayerViewState = PlayerViewState()

    override fun onTriggerEvent(event: PlayerViewEvent) {
        viewModelScope.launch {
            when (event) {
                is PlayerViewEvent.OnPlay -> {
                    play(data = event.data)
                }

                is PlayerViewEvent.OnPlayIconClick -> {
                    onPause()
                }

                is PlayerViewEvent.OnHideShowMiniPlayer -> {
                    hideShowPlayer()
                }

                is PlayerViewEvent.OnPlayNext -> {
                    playNext()
                }

                is PlayerViewEvent.OnPlayPrevious -> {
                    playPrevious()
                }

                is PlayerViewEvent.OnSeekToTime -> {
                    seekToTime(event.position)
                }
            }
        }
    }

    private fun prepare() {
        mediaPlayerController.prepare(
            listener = object : MediaPlayerListener {
                override fun onReady() {
                    setState {
                        currentState.copy(
                            currentTrackData = currentState.trackQueue[currentTrackIndex],
                            isMiniPlayerVisible = true,
                            isLoading = false,
                            isPlaying = true,
                            isAbleToPlay = true,
                        )
                    }
                }

                override fun onAudioCompleted() {
                    setState { currentState.copy(currentTrackIndex = currentTrackIndex + 1) }
                    setState { currentState.copy(currentTrackData = currentState.trackQueue[currentTrackIndex]) }
                }

                override fun onError() {
                    setState { currentState.copy() }
                }
            }
        )
    }

    private fun play(data: Triple<Int, List<TrackData>, String>) {
        setState { currentState.copy(currentTrackIndex = data.first, trackQueue = data.second, playingFrom = data.third) }
        mediaPlayerController.start(currentState.trackQueue.map { it.trackUrl })
        mediaPlayerController.playByIndex(currentState.currentTrackIndex)
    }

    private fun onPause() {
        mediaPlayerController.pause()
        setState { currentState.copy(isPlaying = !currentState.isPlaying) }
    }

    private fun hideShowPlayer() {
        setState { currentState.copy(isMiniPlayerVisible = !currentState.isMiniPlayerVisible) }
    }

    private fun playNext() {
        if (currentState.currentTrackIndex < currentState.trackQueue.size - 1) {
            setState { currentState.copy(currentTrackIndex = currentState.currentTrackIndex + 1) }
            setState { currentState.copy(currentTrackData = currentState.trackQueue[currentTrackIndex]) }
            mediaPlayerController.playNext()
        }
    }

    private fun playPrevious() {
        if (currentState.currentTrackIndex  > 0) {
            setState { currentState.copy(currentTrackIndex = currentState.currentTrackIndex - 1) }
            setState { currentState.copy(currentTrackData = currentState.trackQueue[currentTrackIndex]) }
            mediaPlayerController.playPrevious()
        }
    }

    val playbackTime = mediaPlayerController.playbackTimeFlow()
        .stateIn(
            scope = viewModelScope,
            initialValue = 0L,
            started = SharingStarted.WhileSubscribed(5_000L)
        )


    private fun seekToTime(position: Float) {
        val duration = mediaPlayerController.getDuration().toFloat()
        val time = duration * position
        mediaPlayerController.seekToTime(time.toLong())
    }
}


sealed class PlayerViewEvent() : ViewEvent {
    class OnPlay(val data: Triple<Int, List<TrackData>, String>) : PlayerViewEvent()
    data object OnPlayIconClick : PlayerViewEvent()
    data object OnHideShowMiniPlayer : PlayerViewEvent()
    data object OnPlayNext : PlayerViewEvent()
    data object OnPlayPrevious : PlayerViewEvent()
    class OnSeekToTime(val position: Float) : PlayerViewEvent()
}