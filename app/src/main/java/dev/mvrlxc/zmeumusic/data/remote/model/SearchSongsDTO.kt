package dev.mvrlxc.zmeumusic.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchSongsDTO(
    val album: Album,
    val artists: List<Artist>,
    val category: String,
    val duration: String,
    val duration_seconds: Int,
    val feedbackTokens: FeedbackTokens,
    val inLibrary: Boolean,
    val isExplicit: Boolean,
    val resultType: String,
    val thumbnails: List<Thumbnail>,
    val title: String,
    val videoId: String,
    val videoType: String,
    val year: String?
)