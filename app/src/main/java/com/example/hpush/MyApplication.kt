package com.example.hpush

import android.app.NotificationManager
import com.clevertap.android.sdk.ActivityLifecycleCallback
import com.clevertap.android.sdk.Application
import com.clevertap.android.sdk.CleverTapAPI

class MyApplication : Application() {
    override fun onCreate() {
        ctPreAppCreated()
        super.onCreate()
        ctPostAppCreated()
    }

    private fun ctPreAppCreated() {
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.VERBOSE)
        ActivityLifecycleCallback.register(this)
    }

    private fun ctPostAppCreated() {
        val ctInstance = CleverTapAPI.getDefaultInstance(this)
        CleverTapAPI.createNotificationChannel(
            this, "BRTesting", "Core",
            "Core notifications", NotificationManager.IMPORTANCE_MAX, true
        )
    }
}