package dev.mvrlxc.zmeumusic.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class Album(
    val id: String,
    val name: String
)