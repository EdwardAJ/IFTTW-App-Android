package com.example.tugas_besar_ifttw;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.google.gson.Gson;

import static android.content.Context.ALARM_SERVICE;

public class ControllerNewsRoutine {

    public static void startNewsService (Context context, ModelNews NewsObject, int repeatInterval, int ID) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = getUpdateCurrentPendingIntent(context, NewsObject, ID);
        alarmManager.setInexactRepeating
                (AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + repeatInterval,
                        repeatInterval, pendingIntent);
    }

    public static boolean isPendingIntentRegistered(Context context, ModelNews NewsObject, int ID) {
        return getNoCreatePendingIntent(context, NewsObject, ID) != null;
    }

    public static void cancelPendingIntent(Context context, ModelNews NewsObject, int ID) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(getUpdateCurrentPendingIntent(context, NewsObject, ID));
        }
    }

    public static PendingIntent getNoCreatePendingIntent(Context context, ModelNews NewsObject, int ID) {
        Intent intent = getIntent(context, NewsObject);
        return PendingIntent.getBroadcast(context, ID,
                intent, PendingIntent.FLAG_NO_CREATE);
    }

    public static PendingIntent getUpdateCurrentPendingIntent(Context context, ModelNews NewsObject, int ID) {
        Intent intent  = getIntent(context, NewsObject);
        return PendingIntent.getBroadcast(context, ID,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    public static Intent getIntent(Context context, ModelNews NewsObject) {
        Intent intent = new Intent(context, NewsReceiver.class);
        Gson gson = new Gson();
        String jsonNewsObject = gson.toJson(NewsObject);
        intent.putExtra("NewsObject", jsonNewsObject);
        return intent;
    }
}
