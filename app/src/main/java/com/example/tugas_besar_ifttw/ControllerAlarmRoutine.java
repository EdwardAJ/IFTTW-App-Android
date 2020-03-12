package com.example.tugas_besar_ifttw;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class ControllerAlarmRoutine {

    public static void startAlarmService (Context context, ModelAlarm AlarmObject, int repeatInterval, int ID, Calendar c) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = getIntent(context, AlarmObject);

        PendingIntent pendingIntent = getUpdateCurrentPendingIntent(context, AlarmObject, ID);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID, intent, 0);

        Log.v("CALENDER", String.valueOf(c));

        if (c != null && c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        Log.v("cek_now", String.valueOf(Calendar.getInstance().getTimeInMillis()));
        Log.v("cek_alarm", String.valueOf(c.getTimeInMillis()));
//        Log.v("cek_time", String.valueOf(c.getTimeInMillis().))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
//            alarmManager.setInexactRepeating
//                    (AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + repeatInterval,
//                            repeatInterval, pendingIntent);
        }
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
        Log.v("aoiscka","aisuicuiu12e1");
        return intent;
    }
}
