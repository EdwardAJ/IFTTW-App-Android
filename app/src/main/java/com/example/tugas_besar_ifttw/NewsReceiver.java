package com.example.tugas_besar_ifttw;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class NewsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getStringExtra("action");
        String id = intent.getStringExtra("id");

        Intent sendIntent = new Intent(context, AddRoutineActivity.class);
        Log.v("INTENT", action);
        Log.v("INTENT ID", id);
//        sendIntent.putExtra("result", "true");
//        sendIntent.setAction("com.example.tugas_besar_ifttw.sendNewNews");

//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, sendIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
