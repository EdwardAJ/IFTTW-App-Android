package com.example.tugas_besar_ifttw;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.TimeZone;

import static android.content.Context.ALARM_SERVICE;

public class ControllerAlarmRoutine {

    @TargetApi(26)
    public static void startAlarmService (Context context, ModelAlarm AlarmObject, int ID, Calendar c) {
        long repeatInterval;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        PendingIntent pendingIntent = getUpdateCurrentPendingIntent(context, AlarmObject, ID);

        LocalDateTime ldt = toLocalDateTime(c);

        if (c != null && c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        Log.v("cek_now", String.valueOf(Calendar.getInstance().getTimeInMillis()));
        Log.v("cek_alarm", String.valueOf(c.getTimeInMillis()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            if (AlarmObject.date != null){

                if (AlarmObject.repeat.equals("Everyday")){
                    repeatInterval = 1000*60*60*24;
//                    repeatInterval = 61000;

                    Log.v("pre_calendar", String.valueOf(c));

                    do {
                        if (c.before(Calendar.getInstance())) {
                            c.add(Calendar.DATE, 1);
                        }
                    } while (c.before(Calendar.getInstance()));

                    Log.v("Cek_Calendar", String.valueOf(c.getTime()));

                    long alarm_interval = c.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();

                    Log.v("Cek_c", String.valueOf(c.getTimeInMillis()));
                    Log.v("Cek_calendar", String.valueOf(Calendar.getInstance().getTimeInMillis()));
                    Log.v("Cek_interval", String.valueOf(alarm_interval));
                    Log.v("Cek_system_clock", String.valueOf(SystemClock.elapsedRealtime()));

                    alarmManager.setRepeating
                            (AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+alarm_interval,
                                    AlarmManager.INTERVAL_DAY, pendingIntent);
                    Log.v("Alarm_type", "Daily" );

                } else { //Every particular day
                    repeatInterval = 1000*60*60*24*7;
//                    repeatInterval = 61000;

                    if (AlarmObject.repeat.equals("Every Monday")){
                        c.set(Calendar.DAY_OF_WEEK,1);
                    } else if (AlarmObject.repeat.equals("Every Tuesday")){
                        c.set(Calendar.DAY_OF_WEEK,2);
                    } else if (AlarmObject.repeat.equals("Every Wednesday")){
                        c.set(Calendar.DAY_OF_WEEK,3);
                    } else if (AlarmObject.repeat.equals("Every Thursday")){
                        c.set(Calendar.DAY_OF_WEEK,4);
                    } else if (AlarmObject.repeat.equals("Every Friday")){
                        c.set(Calendar.DAY_OF_WEEK,5);
                    } else if (AlarmObject.repeat.equals("Every Saturday")){
                        c.set(Calendar.DAY_OF_WEEK,6);
                    } else if (AlarmObject.repeat.equals("Every Sunday")){
                        c.set(Calendar.DAY_OF_WEEK,7);
                    }

                    do {
                        if (c.before(Calendar.getInstance())) {
                            c.add(Calendar.WEEK_OF_MONTH, 1);
                        }
                    } while (c.before(Calendar.getInstance()));

                    long alarm_interval = c.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();

                    Log.v("Cek_c", String.valueOf(c.getTimeInMillis()));
                    Log.v("Cek_calendar", String.valueOf(Calendar.getInstance().getTimeInMillis()));
                    Log.v("Cek_interval", String.valueOf(alarm_interval));
                    Log.v("Cek_system_clock", String.valueOf(SystemClock.elapsedRealtime()));

                    alarmManager.setRepeating
                            (AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+alarm_interval,
                                    AlarmManager.INTERVAL_DAY*7, pendingIntent);
                    Log.v("Alarm_type", "Weekly" );

                }

            } else {

                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
                Log.v("Alarm_type", "Single" );

            }
        }
    }


    @TargetApi(26)
    public static LocalDateTime toLocalDateTime(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        TimeZone tz = calendar.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        return LocalDateTime.ofInstant(calendar.toInstant(), zid);
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
