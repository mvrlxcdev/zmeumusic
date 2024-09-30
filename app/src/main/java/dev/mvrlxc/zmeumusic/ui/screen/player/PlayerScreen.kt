package dev.mvrlxc.zmeumusic.ui.screen.player

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.mvrlxc.zmeumusic.R
import dev.mvrlxc.zmeumusic.ui.components.RoundButton
import dev.mvrlxc.zmeumusic.utils.millisToMinutesAndSeconds
import dev.mvrlxc.zmeumusic.utils.timeToMillis
import kotlinx.coroutines.delay

@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val playbackTime by viewModel.playbackTime.collectAsStateWithLifecycle()

    Content(
        trackName = uiState.currentTrackData.name,
        trackArtist = uiState.currentTrackData.artists,
        thumbnailUrl = uiState.currentTrackData.thumbnailUrlMedium,
        trackDuration = uiState.currentTrackData.duration,
        trackTime = millisToMinutesAndSeconds(playbackTime),
        isPlaying = uiState.isPlaying,
        isAbleToPlay = uiState.isAbleToPlay,
        onPlayClick = { viewModel.onTriggerEvent(event = PlayerViewEvent.OnPlayIconClick()) },
        onNextClick = { viewModel.onTriggerEvent(event = PlayerViewEvent.OnPlayNext()) },
        onPreviousClick = { viewModel.onTriggerEvent(event = PlayerViewEvent.OnPlayPrevious()) },
        onValueSliderChange = { viewModel.onTriggerEvent(event = PlayerViewEvent.OnSeekToTime(it)) },
        sliderPosition = playbackTime.toFloat() / timeToMillis(uiState.currentTrackData.duration).toFloat(),)
}

@Composable
private fun Content(
    trackName: String,
    trackArtist: String,
    thumbnailUrl: String,
    isPlaying: Boolean,
    isAbleToPlay: Boolean,
    onPlayClick: () -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
    songNameColor: Color = MaterialTheme.colorScheme.onPrimary,
    songArtistColor: Color = MaterialTheme.colorScheme.onTertiary,
    sliderPosition: Float,
    trackTime: String,
    trackDuration: String,
    onValueSliderChange: (Float) -> Unit,

    ) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(32.dp)
            .fillMaxSize(),

        verticalArrangement = Arrangement.Center,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(thumbnailUrl)
                .crossfade(true).build(),
            placeholder = painterResource(R.drawable.ic_launcher_foreground), //TODO replace with image
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1f)
                .weight(0.3f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(50f))

        )
        Slider(
            value = if (!sliderPosition.isNaN()) sliderPosition else 0f,
            onValueChange = { onValueSliderChange.invoke(it) },
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Text(
                trackTime,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            Text(
                trackDuration,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }
        Text(
            text = trackName,
            style = MaterialTheme.typography.titleMedium,
            color = songNameColor,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = trackArtist,
            style = MaterialTheme.typography.titleSmall,
            color = songArtistColor,
            modifier = Modifier.padding(top = 8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Icon(
                contentDescription = null,
                imageVector = Icons.Default.ArrowBack,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.clickable(onClick = onPreviousClick, enabled = isAbleToPlay)
            )
            RoundButton(
                icon = if (isPlaying) Icons.Default.Close else Icons.Default.PlayArrow,
                onClick = { onPlayClick.invoke() },
                isSelected = true,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .sizeIn(48.dp, 48.dp, 86.dp, 86.dp)
            )
            Icon(
                contentDescription = null,
                imageVector = Icons.Default.ArrowForward,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.clickable(onClick = onNextClick, enabled = isAbleToPlay)
            )
        }
    }
}
