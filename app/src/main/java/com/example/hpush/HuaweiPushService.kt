package com.example.hpush;

import android.app.NotificationManager
import android.os.Bundle
import android.util.Log
import com.clevertap.android.sdk.CleverTapAPI
import com.huawei.hms.push.HmsMessageService
import com.huawei.hms.push.RemoteMessage
import java.lang.Exception

class HuaweiPushService : HmsMessageService() {

    override fun onMessageReceived(data: RemoteMessage?) {
        super.onMessageReceived(data)
        Log.d("HuaweiPushService", "oMR called for hms")
        CleverTapAPI.createNotificationChannel(
            this, "BRTesting", "Core",
            "Core notifications", NotificationManager.IMPORTANCE_HIGH, true
        )
        data?.dataOfMap?.apply {
            try {
                if (isNotEmpty()) {
                    val extras = Bundle()
                    for ((key, value) in this) {
                        extras.putString(key, value)
                    }
                    val info = CleverTapAPI.getNotificationInfo(extras)
                    Log.d(
                        "HuaweiPushService",
                        "onMessageReceived: $info ---- ${info.fromCleverTap}"
                    )
                    if (info.fromCleverTap) {
                        CleverTapAPI.createNotification(applicationContext, extras)
                    } else {
                        //standard fcm notification handling
                    }
                }
            } catch (t: Throwable) {
                Log.d("HuaweiPushService", "Error parsing FCM message", t)
            }
        }
    }

    override fun onNewToken(token: String?, bundle: Bundle?) {
        super.onNewToken(token, bundle)
        Log.d("HuaweiPushService", "onNewToken called for hms")
        CleverTapAPI.getDefaultInstance(this)?.apply {
            if (token != null) {
                pushHuaweiRegistrationId(token, true)
            }
        }
    }

    override fun onTokenError(p0: Exception?) {
        super.onTokenError(p0)
        Log.e("HuaweiPushService","Token error = ${p0?.cause}  ${p0?.message}")
        p0?.printStackTrace()
    }

}
