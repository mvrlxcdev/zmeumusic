package dev.mvrlxc.zmeumusic.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.mvrlxc.zmeumusic.R

@Composable
fun SongCard(
    modifier: Modifier = Modifier,
    songName: String,
    songArtist: String,
    imageUrl: String,
    onClick: () -> Unit,
    onIconClick: () -> Unit,
    isEnabled: Boolean,
    isAble: Boolean = true,
    icon: ImageVector = Icons.Default.MoreVert,
    paddingValues: PaddingValues = PaddingValues(horizontal = 20.dp),
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    underTextColor: Color = MaterialTheme.colorScheme.onTertiary,
    underTextStyle: TextStyle = MaterialTheme.typography.bodySmall,

    ) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(if (isEnabled) Color.DarkGray else backgroundColor)
            .padding(paddingValues)
            .clickable(onClick = onClick, enabled = isAble),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(0.8f)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current).data(imageUrl)
                    .crossfade(true).build(),
                placeholder = painterResource(R.drawable.ic_launcher_foreground), //TODO replace with image
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 16.dp, top = 8.dp, bottom = 8.dp)
                    .size(64.dp)
                    .clip(RoundedCornerShape(15))

            ) //TODO replace with image
            Column() {
                Text(text = songName, color = textColor, style = textStyle, maxLines = 1)
                Text(
                    text = songArtist,
                    color = underTextColor,
                    style = underTextStyle,
                    maxLines = 1
                )
            }
        }
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = underTextColor,
            modifier = Modifier
                .sizeIn(28.dp, 28.dp, 42.dp, 42.dp)
                .clickable(onClick = onIconClick, enabled = isAble)

        )

    }
}