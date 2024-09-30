package dev.mvrlxc.zmeumusic.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Footer() {
    Box(modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .fillMaxWidth()
        .windowInsetsBottomHeight(WindowInsets.systemBars))
}