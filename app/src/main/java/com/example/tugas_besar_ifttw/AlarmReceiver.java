package com.example.tugas_besar_ifttw;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String ALARM_CHANNEL_ID = "alarm_notification_id";
    private ModelAlarm ReceivedAlarmObject;
    private NotificationManager mNotificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Gson gson = new Gson();
        ReceivedAlarmObject = gson.fromJson(intent.getStringExtra("AlarmObj"), ModelAlarm.class);
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Log.v("ID Alarm RECV: ", ReceivedAlarmObject.modelID);
    }

    private void deliverNotification(Context context) {
        Intent contentIntent = new Intent(context, MainActivity.class);

        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, Integer.parseInt(ReceivedAlarmObject.modelID), contentIntent, PendingIntent
                        .FLAG_UPDATE_CURRENT);
        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder
                (context, ALARM_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(ReceivedAlarmObject.notifTitle)
                .setContentText(ReceivedAlarmObject.notifContent)
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        // Deliver the notification
        mNotificationManager.notify(Integer.parseInt(ReceivedAlarmObject.modelID), builder.build());
    }
}
