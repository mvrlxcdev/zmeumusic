package dev.mvrlxc.zmeumusic.data.utils.mappers

import dev.mvrlxc.zmeumusic.data.model.TrackData
import dev.mvrlxc.zmeumusic.data.remote.model.SearchSongsDTO
import dev.mvrlxc.zmeumusic.utils.getTrackLink

fun SearchSongsDTO.toTrackData(): TrackData {
    return TrackData(
        trackUrl = getTrackLink(this.videoId),
        name = this.title,
        artists = this.artists.joinToString(", ") { it.name },
        duration = this.duration,
        thumbnailUrlSmall = this.thumbnails.firstOrNull()?.url ?: "",
        thumbnailUrlMedium = this.thumbnails.lastOrNull()?.url ?: "",
    )
}