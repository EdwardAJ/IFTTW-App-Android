package com.example.tugas_besar_ifttw;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;


public class ControllerSensorRoutine {

    public static void startSensorService (Context context) {
//        if (Build.VERSION.SDK_INT >= 26) {
//            context.startForegroundService(getIntent(context));
//        } else {
//            context.startService(getIntent(context));
//        }
//        Intent newIntent = new Intent(context, ServiceStarter.class);
//        context.sendBroadcast(newIntent);
        context.startService(getIntent(context));
    }

//    public static void startForegroundSensorService (Context context) {
//        context.startForegroundService(getIntent(context));
//    }


    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, ServiceLightSensor.class);
        intent.putExtra("STOP", false);
        return intent;
    }

    public static void stopService(Context context) {
        Intent intent = new Intent(context, ServiceLightSensor.class);
        intent.putExtra("STOP", true);
        context.stopService(intent);
    }
}
