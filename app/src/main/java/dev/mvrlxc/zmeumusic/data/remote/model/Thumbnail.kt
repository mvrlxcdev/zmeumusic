package dev.mvrlxc.zmeumusic.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class Thumbnail(
    val height: Int,
    val url: String,
    val width: Int
)