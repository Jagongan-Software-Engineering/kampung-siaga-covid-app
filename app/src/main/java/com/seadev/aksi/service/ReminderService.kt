package com.seadev.aksi.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.seadev.aksi.R
import com.seadev.aksi.ui.MainActivity
import com.seadev.aksi.ui.SurveyActivity

class ReminderService : FirebaseMessagingService() {
    companion object {
        const val TAG = "ReminderService"
        const val CHANNEL_ID = "Reminder"
        const val CHANNEL_NAME = "Reminder Notification"
    }

    private var notifyId = 0

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.notification != null) {
            val topics = remoteMessage.from!!.split("/".toRegex()).toTypedArray()[2]
            Log.d(TAG, "Notif Token: $topics")
            sendNotification(remoteMessage.notification, topics)
        }
    }

    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.d(TAG, "Refreshed token: $s")
    }

    private fun sendNotification(mNotif: RemoteMessage.Notification?, topics: String) {
        notifyId++
        val activity = if (topics == "update") {
            MainActivity::class.java
        } else {
            SurveyActivity::class.java
        }
        val intent = Intent(this, activity)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_forground)
                .setContentTitle(mNotif!!.title)
                .setContentText(mNotif.body)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /* Create or update. */
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationBuilder.setChannelId(CHANNEL_ID)
            mNotificationManager.createNotificationChannel(channel)
        }
        val notification = notificationBuilder.build()
        mNotificationManager.notify(notifyId, notification)
    }
}