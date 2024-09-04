package com.example.hpush

import android.os.Bundle
import android.os.Looper
import android.util.Log
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.pushnotification.fcm.CTFcmMessageHandler
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFcmMessageListenerService : FirebaseMessagingService() {

    override fun onMessageReceived(data: RemoteMessage) {
        super.onMessageReceived(data)
        data.data.apply {
            try {
                if (isNotEmpty()) {
                    val extras = Bundle()
                    for ((key, value) in this) {
                        extras.putString(key, value)
                    }
                    val info = CleverTapAPI.getNotificationInfo(extras)
                    Log.d(
                        "BestJobsFirebaseMessagingService",
                        "onMessageReceived: $info ---- ${info.fromCleverTap}"
                    )
                    if (info.fromCleverTap) {
                        CleverTapAPI.createNotification(applicationContext, extras)
                    } else {
                        //standard fcm notification handling
                    }
                }
            } catch (t: Throwable) {
                Log.d("MYFCMLIST", "Error parsing FCM message", t)
            }
        }
    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        CleverTapAPI.getDefaultInstance(this)?.apply {
            pushFcmRegistrationId(token, true)
        }
    }
}