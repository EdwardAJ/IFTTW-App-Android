package com.example.tugas_besar_ifttw;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;

public class SensorReceiver extends BroadcastReceiver {

    private ModelSensor ReceivedSensorObjectFromService;

    private WifiManager wifiManager;
    private NotificationManager mNotificationManager;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        Gson gson = new Gson();
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        ReceivedSensorObjectFromService = gson.fromJson(intent.getStringExtra("ACTION"), ModelSensor.class);
        Log.v("ID SENSORSERVICERECV: ", ReceivedSensorObjectFromService.modelID);
        if (ReceivedSensorObjectFromService.action.equals("Wifi Off")) {
            changeWifiOnToOffState(context);
        } else if (ReceivedSensorObjectFromService.action.equals("Wifi On")) {
            changeWifiOffToOnState(context);
        } else {
            deliverNotification(context);
        }
    }

    public void changeWifiOnToOffState(final Context context) {
        Toast.makeText(context, "Wifi Status Is Now Off", Toast.LENGTH_SHORT).show();
        wifiManager.setWifiEnabled(false);
    }

    public void changeWifiOffToOnState(final Context context) {
        Toast.makeText(context, "Wifi Status Is Now On", Toast.LENGTH_SHORT).show();
        wifiManager.setWifiEnabled(true);
    }


    private void deliverNotification(Context context) {
        Intent contentIntent = new Intent(context, MainActivity.class);

        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, Integer.parseInt(ReceivedSensorObjectFromService.modelID), contentIntent, PendingIntent
                        .FLAG_UPDATE_CURRENT);
        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder
                (context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(ReceivedSensorObjectFromService.notifTitle)
                .setContentText(ReceivedSensorObjectFromService.notifContent)
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        // Deliver the notification
        mNotificationManager.notify(Integer.parseInt(ReceivedSensorObjectFromService.modelID), builder.build());
    }
}
