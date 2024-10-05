package dev.mvrlxc.zmeumusic.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun PlaybackSlider(
    modifier: Modifier = Modifier,
    primaryColor: Color = MaterialTheme.colorScheme.onPrimary,
    secondaryColor: Color = MaterialTheme.colorScheme.onTertiary,
    sliderPosition: Float,
    onSliderValueChange: (Float) -> Unit,
    trackTime: String,
    trackDuration: String,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Slider(
            value = if (!sliderPosition.isNaN()) sliderPosition else 0f,
            onValueChange = {onSliderValueChange.invoke(it) },
            // colors = SliderColors(),
            colors = SliderDefaults.colors(
                inactiveTrackColor = secondaryColor,
                inactiveTickColor = secondaryColor,
                activeTrackColor = primaryColor,
                activeTickColor = primaryColor,
            ),
            modifier = Modifier
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                trackTime,
                style = MaterialTheme.typography.labelSmall,
                color = secondaryColor
            )
            Text(
                trackDuration,
                style = MaterialTheme.typography.labelSmall,
                color = secondaryColor
            )
        }
    }
}