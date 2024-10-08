package dev.mvrlxc.zmeumusic.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.mvrlxc.zmeumusic.utils.getScreenHeight
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.min

@SuppressLint("UseOfNonLambdaOffsetOverload")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun PlayerBottomSheetScaffold(
    sheetContent: @Composable() (ColumnScope.() -> Unit),
    onClick: () -> Unit,
    onIconClick: () -> Unit,
    songName: String,
    artistName: String,
    imageUrl: String,
    isPlaying: Boolean,
    isLoading: Boolean = false,
    sheetPeekHeight: Dp,
    content: @Composable (PaddingValues) -> Unit,

    ) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    var offset by remember { mutableFloatStateOf(0f) }
    val maxOffset = getScreenHeight() - 500
    val minOffset = 0f

    val normalizedOffset = when {
        offset < minOffset -> 1f
        offset > maxOffset -> 0f
        else -> min((offset - maxOffset) / (minOffset - maxOffset) * 2, 1f)
    }

    val dragHandleAlpha by animateFloatAsState(
        targetValue = 1f - normalizedOffset, label = "animate drag handle alpha"
    )

    LaunchedEffect(Unit) {
        if (scaffoldState.bottomSheetState.isVisible) {
            while (scaffoldState.bottomSheetState.isVisible) {
                delay(100)
                offset = scaffoldState.bottomSheetState.requireOffset()
            }

        }
    }

    BottomSheetScaffold(
        modifier = Modifier.fillMaxWidth(),
        scaffoldState = scaffoldState,
        sheetPeekHeight = sheetPeekHeight,
        sheetShape = RectangleShape,
        sheetContent = sheetContent,
        sheetContainerColor = Color.Transparent,
        contentColor = Color.Transparent,
        containerColor = Color.Transparent,
        sheetContentColor = Color.Transparent,
        sheetDragHandle = {
            Column(modifier = Modifier.height(80.dp * dragHandleAlpha)) {
                MiniPlayer(
                    modifier = Modifier
                        .graphicsLayer(alpha = dragHandleAlpha),
                    onClick = { scope.launch { scaffoldState.bottomSheetState.expand() } },
                    onIconClick = onIconClick,
                    songName = songName,
                    artistName = artistName,
                    isPlaying = isPlaying,
                    imageUrl = imageUrl,
                    isLoading = isLoading,
                    isAble = dragHandleAlpha == 1f
                )
                Footer()
            }
        }
    ) {
        content(it)
    }
}
