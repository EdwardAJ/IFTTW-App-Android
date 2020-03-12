package com.example.tugas_besar_ifttw;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private ModelAlarm ReceivedAlarmObject;
    private NotificationManager mNotificationManager;
    private WifiManager wifiManager;

    @Override
    public void onReceive(final Context context, Intent intent) {
        wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        Gson gson = new Gson();
        ReceivedAlarmObject = gson.fromJson(intent.getStringExtra("AlarmObj"), ModelAlarm.class);
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Log.v("ID Alarm RECV: ", ReceivedAlarmObject.modelID);
//        Log.v("ID Alarm RECV: ", "1oiwdcqh");
        if (ReceivedAlarmObject.action.equals("Wifi")) {
            changeWifiState(context);
        } else {
            deliverNotification(context);
        }
    }

    public void changeWifiState(final Context context) {
        Toast.makeText(context, "Wifi Status Is Changed!", 5000).show();
        wifiManager.setWifiEnabled(!wifiManager.isWifiEnabled());
    }

    private void deliverNotification(Context context) {
        Intent contentIntent = new Intent(context, MainActivity.class);

        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, Integer.parseInt(ReceivedAlarmObject.modelID), contentIntent, PendingIntent
                        .FLAG_UPDATE_CURRENT);
        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder
                (context, PRIMARY_CHANNEL_ID)
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
