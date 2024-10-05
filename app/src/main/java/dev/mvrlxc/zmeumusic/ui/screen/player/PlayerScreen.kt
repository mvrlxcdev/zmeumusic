package dev.mvrlxc.zmeumusic.ui.screen.player

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.mvrlxc.zmeumusic.data.model.TrackData
import dev.mvrlxc.zmeumusic.ui.components.AsyncThumbnailImage
import dev.mvrlxc.zmeumusic.ui.components.PlaybackSlider
import dev.mvrlxc.zmeumusic.ui.components.RoundButton
import dev.mvrlxc.zmeumusic.ui.theme.ZmeumusicTheme
import dev.mvrlxc.zmeumusic.utils.millisToMinutesAndSeconds
import dev.mvrlxc.zmeumusic.utils.timeToMillis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val playbackTime by viewModel.playbackTime.collectAsStateWithLifecycle()

    Content(
        trackData = uiState.currentTrackData,
        trackTime = millisToMinutesAndSeconds(playbackTime),
        isPlaying = uiState.isPlaying,
        isAbleToPlay = uiState.isAbleToPlay,
        playingFrom = uiState.playingFrom,
        onPlayClick = { viewModel.onTriggerEvent(PlayerViewEvent.OnPlayIconClick) },
        onNextClick = { viewModel.onTriggerEvent(event = PlayerViewEvent.OnPlayNext) },
        onPreviousClick = { viewModel.onTriggerEvent(event = PlayerViewEvent.OnPlayPrevious) },
        onSliderValueChange = { viewModel.onTriggerEvent(event = PlayerViewEvent.OnSeekToTime(it)) },
        sliderPosition = playbackTime.toFloat() / timeToMillis(uiState.currentTrackData.duration).toFloat(),
        onMoreClick = {},
        onPlayingFromClick = {},

        thumbnailUrls = uiState.trackQueue.map { it.thumbnailUrlMedium },
        initialPage = uiState.currentTrackIndex,
        pageCount = uiState.trackQueue.size
    )
}

@Composable
private fun Content(
    trackData: TrackData,
    isPlaying: Boolean,
    isAbleToPlay: Boolean,
    onPlayClick: () -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onMoreClick: () -> Unit,
    onPlayingFromClick: () -> Unit,
    onSliderValueChange: (Float) -> Unit,
    sliderPosition: Float,
    trackTime: String,
    playingFrom: String,


    thumbnailUrls: List<String>,
    initialPage: Int,
    pageCount: Int,

    ) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(32.dp)
            .fillMaxSize(),

        verticalArrangement = Arrangement.Center,
    ) {
        PlayerTopBar(
            playingFrom = playingFrom,
            onMoreClick = onMoreClick,
            onPlayingFromClick = onPlayingFromClick,
            modifier = Modifier.weight(0.2f)
        )
        PlayerPager(
            currentPage = initialPage,
            pageCount = pageCount,
            thumbnailUrls = thumbnailUrls,
            pageChangedToNext = { if (it) onNextClick.invoke() else onPreviousClick() },
            modifier = Modifier.weight(1f)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            TrackInitials(
                trackName = trackData.name,
                trackArtist = trackData.artists,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            PlaybackSlider(
                onSliderValueChange = onSliderValueChange,
                sliderPosition = sliderPosition,
                trackDuration = trackData.duration,
                trackTime = trackTime,
            )
            PlayerButtons(
                onPreviousClick = onPreviousClick,
                isAbleToPlay = isAbleToPlay,
                isPlaying = isPlaying,
                onPlayClick = onPlayClick,
                onNextClick = onNextClick,
            )
        }


    }
}

@Composable
fun TrackInitials(
    modifier: Modifier = Modifier,
    trackName: String,
    trackArtist: String,
    primaryColor: Color = MaterialTheme.colorScheme.onPrimary,
    secondaryColor: Color = MaterialTheme.colorScheme.onTertiary,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxWidth()

    ) {
        Text(
            text = trackName,
            style = MaterialTheme.typography.labelLarge,
            color = primaryColor,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = trackArtist,
            style = MaterialTheme.typography.labelMedium,
            color = secondaryColor,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun PlayerButtons(
    modifier: Modifier = Modifier,
    onPreviousClick: () -> Unit,
    isAbleToPlay: Boolean,
    isPlaying: Boolean,
    onPlayClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()

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

@Composable
private fun PlayerPager(
    modifier: Modifier = Modifier,
    pageCount: Int,
    currentPage: Int,
    thumbnailUrls: List<String>,
    pageChangedToNext: (Boolean) -> Unit,
) {
    if (currentPage > 0) {
        val pagerState = rememberPagerState(initialPage = currentPage) { pageCount }
        var previousPage by remember { mutableIntStateOf(currentPage) }
        var isUserScroll by remember { mutableStateOf(false) }

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collectLatest { page ->
                if (isUserScroll) {
                    pageChangedToNext(page > previousPage)
                    previousPage = pagerState.currentPage
                    isUserScroll = false
                }
            }
        }

        LaunchedEffect(currentPage) {
            pagerState.animateScrollToPage(currentPage)
            previousPage = currentPage
        }

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.isScrollInProgress }
                .collectLatest { isScrolling ->
                    isUserScroll = isScrolling
                }
        }

        HorizontalPager(
            state = pagerState,
            beyondViewportPageCount = 2,
            pageSpacing = 16.dp,
            modifier = Modifier,

            ) { page ->
            AsyncThumbnailImage(url = thumbnailUrls[page])
        }

    }
}

@Composable
private fun PlayerTopBar(
    modifier: Modifier = Modifier,
    playingFrom: String,
    onMoreClick: () -> Unit,
    onPlayingFromClick: () -> Unit,
    ) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                text = "playing from",
                color = MaterialTheme.colorScheme.onTertiary,
                style = MaterialTheme.typography.labelSmall
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onPlayingFromClick.invoke() }
            ) {
                Text(
                    text = playingFrom,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelMedium
                )
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onTertiary
                )
            }
        }
        Icon(
            Icons.Default.MoreVert,
            contentDescription = "more",
            tint = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier
                .sizeIn(32.dp, 32.dp, 48.dp, 48.dp)
                .clickable { onMoreClick.invoke() }
        )
    }
}

@Composable
@Preview
private fun Preview() {
    ZmeumusicTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Content(
                trackData = TrackData(
                    trackUrl = "",
                    name = "bob song",
                    artists = "bob, bobi, bob",
                    duration = "3:22",
                    thumbnailUrlMedium = "",
                    thumbnailUrlSmall = "",
                ),
                playingFrom = "search",
                isPlaying = true,
                isAbleToPlay = true,
                onPlayClick = {},
                onNextClick = {},
                onPreviousClick = {},
                sliderPosition = 0.5f,
                trackTime = "2:28",
                onSliderValueChange = {},

                thumbnailUrls = listOf("", ""),
                initialPage = 1,
                pageCount = 1,
                onMoreClick = {},
                onPlayingFromClick = {}
            )
        }
    }
}


