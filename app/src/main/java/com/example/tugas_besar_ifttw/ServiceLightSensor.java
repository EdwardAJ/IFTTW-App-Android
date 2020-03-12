package com.example.tugas_besar_ifttw;

import android.app.IntentService;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.google.gson.Gson;

public class ServiceLightSensor extends IntentService implements SensorEventListener {

    SensorManager sm = null;
    Sensor lightSensor;
    ModelSensor receivedSensorObject;

    public ServiceLightSensor() {
        super("Start ServiceLightSensor");
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] >= receivedSensorObject.sensorValue) {
            createNewIntent();
        }
    }

    public void createNewIntent() {
        Intent intent = new Intent(getApplicationContext(), SensorReceiver.class);
        Gson gson = new Gson();
        String sendSensorObject = gson.toJson(receivedSensorObject);
        intent.putExtra("ID", "ACTION");
        intent.putExtra("ACTION", sendSensorObject);
        sendBroadcast(intent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onHandleIntent(Intent workIntent) {
        if (workIntent.getBooleanExtra("STOP", true)) {
            stopSelf();
            return;
        }

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        Gson gson = new Gson();
        receivedSensorObject = gson.fromJson(workIntent.getStringExtra("SensorObject"), ModelSensor.class);
        sm.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        while(true);
    }

//    @Override
//    public void onDestroy() {
//        sm.unregisterListener(this);
//    }
}
