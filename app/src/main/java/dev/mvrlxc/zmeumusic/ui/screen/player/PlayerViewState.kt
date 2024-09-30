package dev.mvrlxc.zmeumusic.ui.screen.player

import dev.mvrlxc.zmeumusic.data.model.TrackData
import dev.mvrlxc.zmeumusic.data.remote.model.SearchSongsDTO
import dev.mvrlxc.zmeumusic.ui.base.ViewState

data class PlayerViewState(
    val isPlaying: Boolean = false,
    val isAbleToPlay: Boolean = true,
    val isLoading: Boolean = true,
    val isMiniPlayerVisible: Boolean = false,
    val currentTrackData: TrackData = TrackData(),
) : ViewState