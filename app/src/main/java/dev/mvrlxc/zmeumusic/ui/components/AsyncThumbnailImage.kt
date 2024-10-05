package dev.mvrlxc.zmeumusic.ui.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.mvrlxc.zmeumusic.R

@Composable
fun AsyncThumbnailImage(
    modifier: Modifier = Modifier,
    url: String
) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current).data(url)
            .crossfade(true).build(),
        placeholder = painterResource(R.drawable.ic_launcher_background), //TODO replace with image
        contentScale = ContentScale.Crop,
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxWidth()
            .clip(RoundedCornerShape(50f))


    )
}