package dev.mvrlxc.zmeumusic.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class Artist(
    val id: String,
    val name: String
)