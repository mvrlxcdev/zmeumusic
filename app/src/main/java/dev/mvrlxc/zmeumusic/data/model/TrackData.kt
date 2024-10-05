package dev.mvrlxc.zmeumusic.data.model

data class TrackData(
    val trackUrl: String,
    val name: String,
    val artists: String,
    val duration: String,
    val thumbnailUrlSmall: String,
    val thumbnailUrlMedium: String,
) {
    companion object {
        fun init() = TrackData(
            trackUrl = "",
            name = "",
            artists = "",
            duration = "0:00",
            thumbnailUrlSmall = "",
            thumbnailUrlMedium = "",
        )
    }
}
