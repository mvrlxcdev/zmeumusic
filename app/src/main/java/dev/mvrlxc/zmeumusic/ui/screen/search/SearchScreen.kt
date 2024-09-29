package dev.mvrlxc.zmeumusic.ui.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.mvrlxc.zmeumusic.data.remote.model.SearchSongsDTO
import dev.mvrlxc.zmeumusic.ui.components.SearchTextField
import dev.mvrlxc.zmeumusic.ui.components.SongCard

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onCardClick: (Pair<Int, List<SearchSongsDTO>>) -> Unit,
    activeSongID: String,
    isAbleToPlay: Boolean,
) {
    val uiState by viewModel.uiState.collectAsState()
    //  if (uiState.isError) Text(uiState.errorMessage, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground) else


    Content(
        isLoading = uiState.isLoading,
        isError = uiState.isError,
        errorMessage = uiState.errorMessage,
        textFieldValue = uiState.textFieldValue,
        songsData = uiState.songs,
        activeSongID = activeSongID,
        isAbleToPlay = isAbleToPlay,
        onTextChange = { viewModel.onTriggerEvent(SearchViewEvent.OnTextChange(it)) },
        onCardClick = { onCardClick.invoke(it) }
    )


}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isAbleToPlay: Boolean,
    isError: Boolean,
    errorMessage: String,
    textFieldValue: String,
    activeSongID: String,
    onTextChange: (String) -> Unit,
    songsData: List<SearchSongsDTO>,
    onCardClick: (Pair<Int, List<SearchSongsDTO>>) -> Unit,


    ) {
    Column(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
        SearchTextField(
            value = textFieldValue,
            onValueChange = onTextChange,
            labelText = "songs, artist...",
            modifier = Modifier.fillMaxWidth(),
            borderWidth = 1.dp,
        )
        if (isLoading) {
            Text("loading", color = MaterialTheme.colorScheme.onBackground)
        } else
            if (isError) {
                Text(errorMessage, color = MaterialTheme.colorScheme.onBackground)
            } else
                LazyColumn {
                    itemsIndexed(songsData) { index, item ->
                        SongCard(
                            isAble = isAbleToPlay,
                            isEnabled = activeSongID == item.videoId,
                            onClick = { onCardClick(Pair(index, songsData)) },
                            onIconClick = {},
                            songName = item.title,
                            songArtist = item.artists.joinToString(", ") { it.name },
                            imageUrl = item.thumbnails[1].url,
                        )
                    }
                }
    }
}