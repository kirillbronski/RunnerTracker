package com.bronski.android.runnertracker.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
    }

    private fun setupTimber() {
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String? {
                return String.format(
                    "%s: %s: %s",
                    element.fileName,
                    element.methodName,
                    element.lineNumber,
                    super.createStackElementTag(element)
                )
            }

            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                super.log(priority, "Timber: $tag", message, t)
            }
        })
    }
}