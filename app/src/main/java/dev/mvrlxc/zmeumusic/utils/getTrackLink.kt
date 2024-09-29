package dev.mvrlxc.zmeumusic.utils

import dev.mvrlxc.zmeumusic.utils.Constants.BASE_URL

fun getTrackLink(videoId: String): String {
    return "http://$BASE_URL/getLinkAAC?url=https://www.youtube.com/watch?v=$videoId"
}