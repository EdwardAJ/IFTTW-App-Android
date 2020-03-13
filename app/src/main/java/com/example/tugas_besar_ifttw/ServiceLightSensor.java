package com.example.tugas_besar_ifttw;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ServiceLightSensor extends IntentService implements SensorEventListener {

    SensorManager sm = null;
    Sensor lightSensor;
    ArrayList<ModelSensor> receivedSensorObjects;
    DatabaseHelper database;

    public ServiceLightSensor() {
        super("Start ServiceLightSensor");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//        Log.v("ReceivedSensorObjects: ", String.valueOf(receivedSensorObjects.size()));
        for (ModelSensor receivedSensorObject: receivedSensorObjects) {
            if (event.values[0] >= receivedSensorObject.sensorValue) {
//                Log.v("Value: ", String.valueOf(receivedSensorObject.sensorValue));
                createNewIntent(receivedSensorObject);
            }
        }
    }

    public void createNewIntent(ModelSensor receivedSensorObject) {
        Intent intent = new Intent(getApplicationContext(), SensorReceiver.class);
        Gson gson = new Gson();
        String sendSensorObject = gson.toJson(receivedSensorObject);
        intent.putExtra("ACTION", sendSensorObject);
        sendBroadcast(intent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    protected void onHandleIntent(Intent workIntent) {

        if (workIntent.getBooleanExtra("STOP", true)) {
            Log.v("GET_INTENT", "KILLED");
            stopSelf();
            return;
        }

        Log.v("GET_INTENT", "HUAAA");

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);

        database = new DatabaseHelper(getApplicationContext());
        receivedSensorObjects = new ArrayList<>();

        Cursor result = database.getAllDataWithServiceCondition();
        while (result.moveToNext()) {
            Log.v("GET_INTENT?", "JALAN");
            String modelID = result.getString(0);
            String action = result.getString(1);
            double value = result.getDouble(8);
            String condName = result.getString(7);
            String notifTitle = result.getString(2);
            String notifContent = result.getString(3);

            ModelSensor SensorObject = new ModelSensor(modelID, action, value, condName);
            SensorObject.setNotifAttributes(notifTitle, notifContent);
            receivedSensorObjects.add(SensorObject);
        }

        sm.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        while(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();

            startForeground(1, notification);
        }
    }


    @Override
    public void onDestroy() {
        if (sm != null) {
            sm.unregisterListener(this);
        }
        Intent reIntent = new Intent(getApplicationContext(), ServiceStarter.class);
        sendBroadcast(reIntent);
        super.onDestroy();
        Log.v("Destroyed: ", "Destroyed");
    }



}
