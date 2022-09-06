package com.bronski.android.runnertracker.core.utils

import android.annotation.SuppressLint
import android.content.Context
import com.bronski.android.runnertracker.R
import com.bronski.android.runnertracker.core.data.room.RunEntity
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.google.android.material.textview.MaterialTextView
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("ViewConstructor")
class CustomMarkerView(
    private val runs: List<RunEntity>,
    context: Context,
    layoutId: Int,
) : MarkerView(context, layoutId) {

    override fun getOffset(): MPPointF {
        return MPPointF(-width / 2f, -height.toFloat())
    }

    @SuppressLint("SetTextI18n")
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)
        if (e == null) {
            return
        }

        val currentRunId = e.x.toInt()
        val run = runs[currentRunId]

        val calendar = Calendar.getInstance().apply {
            timeInMillis = run.timestamp
        }
        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.ROOT)

        findViewById<MaterialTextView>(R.id.date_text_view).text =
            dateFormat.format(calendar.time)
        findViewById<MaterialTextView>(R.id.average_speed_text_view).text =
            "${run.averageSpeedInKmh}km/h"
        findViewById<MaterialTextView>(R.id.distance_text_view).text =
            "${run.distanceInMeters / 1000f}km"
        findViewById<MaterialTextView>(R.id.duration_text_view).text =
            TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)
        findViewById<MaterialTextView>(R.id.calories_text_view).text =
            "${run.caloriesBurned}kcal"
    }
}