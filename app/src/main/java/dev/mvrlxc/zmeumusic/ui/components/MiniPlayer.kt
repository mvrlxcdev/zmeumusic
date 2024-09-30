package dev.mvrlxc.zmeumusic.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
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
    Box(
        modifier = Modifier
            .background(backgroundColor)
            .height(80.dp),
    ) {
        SongCard(
            modifier = modifier,
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