package com.bronski.android.runnertracker.di.modules

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.bronski.android.runnertracker.R
import com.bronski.android.runnertracker.core.ui.MainActivity
import com.bronski.android.runnertracker.core.utils.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import com.bronski.android.runnertracker.core.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped


@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @SuppressLint("VisibleForTests")
    @Provides
    @ServiceScoped
    fun provideFusedLocationProviderClient(
        @ApplicationContext appContext: Context,
    ) = FusedLocationProviderClient(appContext)

    @SuppressLint("ServiceCast")
    @Provides
    @ServiceScoped
    fun provideNotificationManager(
        @ApplicationContext appContext: Context,
    ) = appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    @RequiresApi(Build.VERSION_CODES.S)
    @Provides
    @ServiceScoped
    fun provideMainActivityPendingIntent(
        @ApplicationContext appContext: Context,
    ) = PendingIntent.getActivity(
        appContext,
        0,
        Intent(appContext, MainActivity::class.java).also {
            it.action = ACTION_SHOW_TRACKING_FRAGMENT
        },
        PendingIntent.FLAG_MUTABLE
    )

    @Provides
    @ServiceScoped
    fun provideBaseNotification(
        @ApplicationContext appContext: Context,
        pendingIntent: PendingIntent,
    ) = NotificationCompat.Builder(appContext, NOTIFICATION_CHANNEL_ID)
        .setAutoCancel(false)
        .setOngoing(true)
        .setSmallIcon(R.drawable.ic_run_24)
        .setContentTitle("Running App")
        .setContentText("00:00:00")
        .setContentIntent(pendingIntent)
}