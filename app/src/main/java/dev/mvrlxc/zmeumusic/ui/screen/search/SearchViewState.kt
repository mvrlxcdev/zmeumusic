package dev.mvrlxc.zmeumusic.ui.screen.search

import dev.mvrlxc.zmeumusic.data.remote.model.SearchSongsDTO
import dev.mvrlxc.zmeumusic.ui.base.ViewState

data class SearchViewState (
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val textFieldValue: String = "",
    val songs: List<SearchSongsDTO> = emptyList(),
    val activeSongIndex: Int = -1,
): ViewState