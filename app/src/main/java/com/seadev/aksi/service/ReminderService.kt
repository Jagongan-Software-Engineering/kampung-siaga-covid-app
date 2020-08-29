package com.seadev.aksi.service;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.seadev.aksi.R;
import com.seadev.aksi.ui.MainActivity;
import com.seadev.aksi.ui.SurveyActivity;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

public class ReminderService extends FirebaseMessagingService {

    private String TAG = "ReminderService";

    public ReminderService() {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null) {
            String topics = remoteMessage.getFrom().split("/")[2];
            Log.d(TAG, "Notif Token: " + topics);
            sendNotification(remoteMessage.getNotification(), topics);
        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d(TAG, "Refreshed token: " + s);
    }

    private void sendNotification(RemoteMessage.Notification mNotif, String topics) {
        String channelId = getString(R.string.default_notification_channel_id);
        String channelName = getString(R.string.default_notification_channel_name);
        Class activity;
        if (topics.equals("update")) {
            activity = MainActivity.class;
        } else {
            activity = SurveyActivity.class;
        }
        Intent intent = new Intent(this, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_forground)
                        .setContentTitle(mNotif.getTitle())
                        .setContentText(mNotif.getBody())
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationBuilder.setChannelId(channelId);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }
        Notification notification = notificationBuilder.build();
        if (mNotificationManager != null) {
            mNotificationManager.notify(0, notification);
        }

    }
}
