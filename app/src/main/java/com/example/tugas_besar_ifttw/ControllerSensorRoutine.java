package com.example.tugas_besar_ifttw;


import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.google.gson.Gson;

import static android.content.Context.ALARM_SERVICE;

public class ControllerSensorRoutine {

    public static void startSensorService (Context context, ModelSensor SensorObject) {
        context.sendBroadcast(getIntent(context, SensorObject));
    }

    public static Intent getIntent(Context context, ModelSensor SensorObject) {
        Intent intent = new Intent(context, SensorReceiver.class);
        Gson gson = new Gson();
        String jsonSensorObject = gson.toJson(SensorObject);
        intent.putExtra("ID", "ObjectFromFragment");
        intent.putExtra("SensorObject", jsonSensorObject);
        return intent;
    }

    public static void stopService(Context context) {
        Intent intent = new Intent(context, ServiceLightSensor.class);
        intent.putExtra("STOP", true);
        context.startService(intent);
    }
}
