package dev.mvrlxc.zmeumusic.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MiniPlayer(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onIconClick: () -> Unit,
    songName: String,
    artistName: String,
    imageUrl: String,
    isPlaying: Boolean,
    isLoading: Boolean = false,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    isAble: Boolean = true,
) {
    Box(modifier = Modifier.background(backgroundColor)) {
        SongCard(
            modifier = modifier.height(72.dp),
            isEnabled = false,
            onClick = onClick,
            onIconClick = onIconClick,
            songName = songName,
            songArtist = artistName,
            icon = if (!isLoading) Icons.Default.Email else if (isPlaying) Icons.Default.Close else Icons.Default.PlayArrow,
            imageUrl = imageUrl,
            isAble = isAble,
        )
    }
}