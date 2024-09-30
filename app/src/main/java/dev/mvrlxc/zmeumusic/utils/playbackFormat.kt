package dev.mvrlxc.zmeumusic.utils

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
fun millisToMinutesAndSeconds(millis: Long): String {
    val totalSeconds = millis / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60

    return String.format("%d:%02d", minutes, seconds)
}

fun timeToMillis(time: String): Long {
    val parts = time.split(":")
    if (parts.size != 2) throw IllegalArgumentException("Invalid time format. Expected format is M:SS")

    val minutes = parts[0].toLongOrNull() ?: throw IllegalArgumentException("Invalid minutes value")
    val seconds = parts[1].toLongOrNull() ?: throw IllegalArgumentException("Invalid seconds value")

    // 1 minute = 60 seconds, 1 second = 1000 milliseconds
    return (minutes * 60 + seconds) * 1000
}