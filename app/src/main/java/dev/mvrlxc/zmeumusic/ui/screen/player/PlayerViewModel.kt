package dev.mvrlxc.zmeumusic.ui.screen.player

import androidx.lifecycle.viewModelScope
import dev.mvrlxc.zmeumusic.data.remote.model.SearchSongsDTO
import dev.mvrlxc.zmeumusic.domain.player.MediaPlayerController
import dev.mvrlxc.zmeumusic.domain.player.MediaPlayerListener
import dev.mvrlxc.zmeumusic.ui.base.BaseViewModel
import dev.mvrlxc.zmeumusic.ui.base.ViewEvent
import dev.mvrlxc.zmeumusic.utils.getTrackLink
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val mediaPlayerController: MediaPlayerController,
) : BaseViewModel<PlayerViewState, PlayerViewEvent>() {
    override fun createInitialState(): PlayerViewState = PlayerViewState()

    override fun onTriggerEvent(event: PlayerViewEvent) {
        viewModelScope.launch {
            when (event) {
                is PlayerViewEvent.OnPlay -> {
                    play(data = event.data)
                }

                is PlayerViewEvent.OnPlayIconClick -> {
                    onResumePause()
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
            }
        }
    }

    private var currentTrackIndex: Int = -1
    private lateinit var trackList: List<SearchSongsDTO>

    private fun play(data: Pair<Int, List<SearchSongsDTO>>) {
        currentTrackIndex = data.first
        trackList = data.second.map { it }
        setState { currentState.copy(isAbleToPlay = false, isLoading = true) }

        mediaPlayerController.prepare(
            pathSource = getTrackLink(trackList[currentTrackIndex].videoId),
            listener = object : MediaPlayerListener {
                override fun onReady() {
                    mediaPlayerController.start()
                    updateSongState(trackList[currentTrackIndex])
                    trackList.subList(1, trackList.size)
                        .forEach { mediaPlayerController.addTrackToQueue(getTrackLink(it.videoId)) }
                    //    mediaPlayerController.addTrackToQueue(getTrackLink(trackList[currentTrackIndex + 1].videoID))
                }

                override fun onAudioCompleted() {
                    if (currentTrackIndex < trackList.size - 1) {
                        currentTrackIndex++
                        //    mediaPlayerController.addTrackToQueue(getTrackLink(trackList[currentTrackIndex + 1].videoID))
                        updateSongState(trackList[currentTrackIndex])
                    } else {
                        mediaPlayerController.stop()
                    }
                }

                override fun onError() {

                }

            }
        )
    }

    private fun updateSongState(
        songDetails: SearchSongsDTO,
    ) {
        setState {
            currentState.copy(
                songName = songDetails.title,
                songArtist = songDetails.artists.joinToString(separator = ", ") { it.name },
                imageUrl = songDetails.thumbnails[1].url,
                isAbleToPlay = true,
                isPlaying = true,
                isLoading = false,
                isMiniPlayerVisible = true,
                currentTrackID = songDetails.videoId,
            )
        }
    }

    private fun onResumePause() {
        if (currentState.isPlaying) {
            mediaPlayerController.pause()
            setState { currentState.copy(isPlaying = false) }
        } else {
            mediaPlayerController.start()
            setState { currentState.copy(isPlaying = true) }
        }
    }

    private fun hideShowPlayer() {
        setState { currentState.copy(isMiniPlayerVisible = !currentState.isMiniPlayerVisible) }
    }

    private suspend fun playNext() {
        if (currentTrackIndex < trackList.size - 1) {
            currentTrackIndex++
            updateSongState(trackList[currentTrackIndex])
            //  setState { currentState.copy(isAbleToPlay = false) }
            mediaPlayerController.playNext()
            //  delay(1000)
            // setState { currentState.copy(isAbleToPlay = true) }

        }
    }

    private suspend fun playPrevious() {
        if (currentTrackIndex > 0) {
            currentTrackIndex--
            updateSongState(trackList[currentTrackIndex])
            mediaPlayerController.playPrevious()
        }
    }

}


sealed class PlayerViewEvent() : ViewEvent {
    class OnPlay(val data: Pair<Int, List<SearchSongsDTO>>) : PlayerViewEvent()
    class OnPlayIconClick() : PlayerViewEvent()
    class OnHideShowMiniPlayer() : PlayerViewEvent()
    class OnPlayNext() : PlayerViewEvent()
    class OnPlayPrevious() : PlayerViewEvent()
}