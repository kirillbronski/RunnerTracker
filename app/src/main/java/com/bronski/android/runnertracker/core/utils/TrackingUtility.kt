package com.bronski.android.runnertracker.core.utils

import android.location.Location
import com.bronski.android.runnertracker.core.services.Polyline
import com.bronski.android.runnertracker.core.utils.Constants.TEN
import java.util.concurrent.TimeUnit

object TrackingUtility {

    fun getFormattedStopWatchTime(ms: Long, includeMillis: Boolean = false): String {
        var milliseconds = ms
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        milliseconds -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
        if (!includeMillis) {
            return "${if (hours < TEN) "0" else ""}$hours:" +
                    "${if (minutes < TEN) "0" else ""}$minutes:" +
                    "${if (seconds < TEN) "0" else ""}$seconds"
        }
        milliseconds -= TimeUnit.SECONDS.toMillis(seconds)
        milliseconds /= 10
        return "${if (hours < TEN) "0" else ""}$hours:" +
                "${if (minutes < TEN) "0" else ""}$minutes:" +
                "${if (seconds < TEN) "0" else ""}$seconds:" +
                "${if (milliseconds < TEN) "0" else ""}$milliseconds"
    }

    fun calculatePolylineLength(polyline: Polyline): Float {
        var distance = 0f
        for (i in 0..polyline.size - 2) {
            val position1 = polyline[i]
            val position2 = polyline[i + 1]
            val result = FloatArray(1)
            Location.distanceBetween(
                position1.latitude,
                position1.longitude,
                position2.latitude,
                position2.longitude,
                result
            )
            distance += result[0]
        }
        return distance
    }

}