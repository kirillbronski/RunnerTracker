package com.bronski.android.runnertracker.core.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent.FLAG_MUTABLE
import android.app.PendingIntent.getService
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bronski.android.runnertracker.R
import com.bronski.android.runnertracker.core.utils.Constants.ACTION_PAUSE_SERVICE
import com.bronski.android.runnertracker.core.utils.Constants.ACTION_START_OR_RESUME_SERVICE
import com.bronski.android.runnertracker.core.utils.Constants.ACTION_STOP_SERVICE
import com.bronski.android.runnertracker.core.utils.Constants.FASTEST_LOCATION_INTERVAL
import com.bronski.android.runnertracker.core.utils.Constants.LOCATION_UPDATE_INTERVAL
import com.bronski.android.runnertracker.core.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.bronski.android.runnertracker.core.utils.Constants.NOTIFICATION_CHANNEL_NAME
import com.bronski.android.runnertracker.core.utils.Constants.NOTIFICATION_ID
import com.bronski.android.runnertracker.core.utils.Constants.ONE_SECOND
import com.bronski.android.runnertracker.core.utils.Constants.TIMER_UPDATE_INTERVAL
import com.bronski.android.runnertracker.core.utils.Constants.TIME_DEFAULT_VALUE
import com.bronski.android.runnertracker.core.utils.TrackingUtility
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

typealias Polyline = MutableList<LatLng>
typealias SeveralPolyline = MutableList<Polyline>

@AndroidEntryPoint
class TrackingService : LifecycleService() {

    private var isFirstRun = true
    private var serviceKilled = false
    private var isTimerEnabled = false
    private var lapTime = TIME_DEFAULT_VALUE
    private var timeRun = TIME_DEFAULT_VALUE
    private var timeStarted = TIME_DEFAULT_VALUE
    private var lastSecondTimestamp = TIME_DEFAULT_VALUE

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @Inject
    lateinit var baseNotificationBuilder: NotificationCompat.Builder

    @Inject
    lateinit var notificationManager: NotificationManager

    private lateinit var currentNotificationBuilder: NotificationCompat.Builder

    private val timeRunInSeconds = MutableLiveData<Long>()

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            if (isTracking.value == true) {
                result.locations.let {
                    for (location in it) {
                        addPathPoint(location)
                        Timber.d("New Location: ${location.latitude}, ${location.longitude}")
                    }
                }
            }
        }
    }

    companion object {
        val timeRunInMillis = MutableLiveData<Long>()
        val isTracking = MutableLiveData<Boolean>()
        val pathPoints = MutableLiveData<SeveralPolyline>()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("VisibleForTests")
    override fun onCreate() {
        super.onCreate()
        currentNotificationBuilder = baseNotificationBuilder
        postInitialValues()

        isTracking.observe(this, Observer {
            updateLocationTracking(it)
            updateNotificationTrackingState(it)
        })
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    if (isFirstRun) {
                        startForegroundService()
                        isFirstRun = false
                    } else {
                        startTimer()
                        Timber.d("Resuming service...")
                    }
                    Timber.d("Started or resumed service")
                }
                ACTION_PAUSE_SERVICE -> {
                    pauseService()
                    Timber.d("Paused service")
                }
                ACTION_STOP_SERVICE -> {
                    killService()
                    Timber.d("Stopped service")
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startTimer() {
        addEmptyPolyline()
        isTracking.postValue(true)
        timeStarted = System.currentTimeMillis()
        isTimerEnabled = true
        CoroutineScope(Dispatchers.Main).launch {
            while (isTracking.value == true) {
                lapTime = System.currentTimeMillis() - timeStarted
                timeRunInMillis.postValue(timeRun + lapTime)
                if (timeRunInMillis.value!! >= lastSecondTimestamp + ONE_SECOND) {
                    timeRunInSeconds.postValue(timeRunInSeconds.value?.plus(1))
                    lastSecondTimestamp += ONE_SECOND
                }
                delay(TIMER_UPDATE_INTERVAL)
            }
            timeRun += lapTime
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationTracking(isTracking: Boolean) {
        if (isTracking) {
            val locationRequest = LocationRequest.create().apply {
                interval = LOCATION_UPDATE_INTERVAL
                fastestInterval = FASTEST_LOCATION_INTERVAL
                priority = PRIORITY_HIGH_ACCURACY
            }
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } else {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun updateNotificationTrackingState(isTracking: Boolean) {
        val notificationActionText = if (isTracking) "Pause" else "Resume"
        val pendingIntent = if (isTracking) {
            val pauseIntent = Intent(this, TrackingService::class.java).apply {
                action = ACTION_PAUSE_SERVICE
            }
            getService(this, 1, pauseIntent, FLAG_MUTABLE)
        } else {
            val resumeIntent = Intent(this, TrackingService::class.java).apply {
                action = ACTION_START_OR_RESUME_SERVICE
            }
            getService(this, 2, resumeIntent, FLAG_MUTABLE)
        }

        currentNotificationBuilder.javaClass.getDeclaredField("mActions").apply {
            isAccessible = true
            set(currentNotificationBuilder, ArrayList<NotificationCompat.Action>())
        }

        if (!serviceKilled) {
            currentNotificationBuilder = baseNotificationBuilder
                .addAction(R.drawable.ic_baseline_pause_24, notificationActionText, pendingIntent)
            notificationManager.notify(NOTIFICATION_ID, currentNotificationBuilder.build())
        }

    }

    private fun addPathPoint(location: Location?) {
        location?.let {
            val position = LatLng(location.latitude, location.longitude)
            pathPoints.value?.apply {
                last().add(position)
                pathPoints.postValue(this)
            }
        }
    }

    private fun postInitialValues() {
        isTracking.postValue(false)
        pathPoints.postValue(mutableListOf())
        timeRunInSeconds.postValue(TIME_DEFAULT_VALUE)
        timeRunInMillis.postValue(TIME_DEFAULT_VALUE)
    }

    private fun addEmptyPolyline() =
        pathPoints.value?.apply {
            add(mutableListOf())
            pathPoints.postValue(this)
        } ?: pathPoints.postValue(mutableListOf(mutableListOf()))

    @RequiresApi(Build.VERSION_CODES.S)
    private fun startForegroundService() {
        startTimer()
        isTracking.postValue(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        startForeground(NOTIFICATION_ID, baseNotificationBuilder.build())

        timeRunInSeconds.observe(this) {
            if (!serviceKilled) {
                val notification = currentNotificationBuilder
                    .setContentText(TrackingUtility.getFormattedStopWatchTime(ms = it * ONE_SECOND))
                notificationManager.notify(NOTIFICATION_ID, notification.build())
            }
        }
    }

    private fun pauseService() {
        isTracking.postValue(false)
        isTimerEnabled = false
    }

    private fun killService() {
        serviceKilled = true
        isFirstRun = true
        pauseService()
        postInitialValues()
        stopForeground(true)
        stopSelf()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }
}