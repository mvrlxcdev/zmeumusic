package dev.mvrlxc.zmeumusic.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class FeedbackTokens(
    val add: String,
    val remove: String
)