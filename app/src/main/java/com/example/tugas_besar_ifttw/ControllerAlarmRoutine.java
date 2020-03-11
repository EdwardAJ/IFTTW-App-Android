package com.example.tugas_besar_ifttw;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.google.gson.Gson;

import static android.content.Context.ALARM_SERVICE;

public class ControllerAlarmRoutine {

    public static void startAlarmService (Context context, ModelAlarm AlarmObject, int repeatInterval, int ID) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = getUpdateCurrentPendingIntent(context, AlarmObject, ID);
        alarmManager.setInexactRepeating
                (AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + repeatInterval,
                        repeatInterval, pendingIntent);
    }

    public static boolean isPendingIntentRegistered(Context context, ModelAlarm AlarmObj, int ID) {
        return getNoCreatePendingIntent(context, AlarmObj, ID) != null;
    }

    public static void cancelPendingIntent(Context context, ModelAlarm AlarmObj, int ID) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(getUpdateCurrentPendingIntent(context, AlarmObj, ID));
        }
    }

    public static PendingIntent getNoCreatePendingIntent(Context context, ModelAlarm AlarmObj, int ID) {
        Intent intent = getIntent(context, AlarmObj);
        return PendingIntent.getBroadcast(context, ID,
                intent, PendingIntent.FLAG_NO_CREATE);
    }

    public static PendingIntent getUpdateCurrentPendingIntent(Context context, ModelAlarm AlarmObj, int ID) {
        Intent intent  = getIntent(context, AlarmObj);
        return PendingIntent.getBroadcast(context, ID,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    public static Intent getIntent(Context context, ModelAlarm AlarmObj) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        Gson gson = new Gson();
        String jsonAlarmObject = gson.toJson(AlarmObj);
        intent.putExtra("AlarmObj", jsonAlarmObject);
        return intent;
    }
}
