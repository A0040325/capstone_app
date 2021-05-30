package com.example.capstone

import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MainFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMsg: RemoteMessage) {
        super.onMessageReceived(remoteMsg)

        remoteMsg.notification?.let {
            Log.d("HEHE", it.body.toString())

            val notification =
                NotificationCompat.Builder(this, getString(R.string.notification_channel))
                    .setContentTitle(remoteMsg.from)
                    .setContentText(it.body)
                    .setSmallIcon(R.drawable.barong1)
                    .build()

            val manager = NotificationManagerCompat.from(applicationContext)
            manager.notify(0, notification)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}