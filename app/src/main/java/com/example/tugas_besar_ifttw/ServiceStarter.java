package com.example.tugas_besar_ifttw;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ServiceStarter extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("INTENT", "RECEIVE");

        Intent newIntent = new Intent(context, ServiceLightSensor.class);
        newIntent.putExtra("STOP", false);

        context.startForegroundService(newIntent);
    }
}
