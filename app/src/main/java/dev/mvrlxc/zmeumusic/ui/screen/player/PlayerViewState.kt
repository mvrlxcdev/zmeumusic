package dev.mvrlxc.zmeumusic.ui.screen.player

import dev.mvrlxc.zmeumusic.data.remote.model.SearchSongsDTO
import dev.mvrlxc.zmeumusic.ui.base.ViewState

data class PlayerViewState(
    val songName: String = "",
    val songArtist: String = "",
    val imageUrl: String = "",
    val isPlaying: Boolean = false,
    val isAbleToPlay: Boolean = true,
    val isLoading: Boolean = true,
    val isMiniPlayerVisible: Boolean = false,
    val currentTrackID: String = "",
    val trackQueue: List<SearchSongsDTO> = emptyList(),
    val currentTrackIndex: Int = 0,

    ) : ViewState