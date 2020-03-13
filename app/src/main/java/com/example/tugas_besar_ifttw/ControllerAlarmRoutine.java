package com.example.tugas_besar_ifttw;

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
import java.util.Calendar;
import java.util.TimeZone;

import static android.content.Context.ALARM_SERVICE;

public class ControllerAlarmRoutine {

    public static void startAlarmService (Context context, ModelAlarm AlarmObject, int repeatInterval, int ID, Calendar c) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = getIntent(context, AlarmObject);

        PendingIntent pendingIntent = getUpdateCurrentPendingIntent(context, AlarmObject, ID);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID, intent, 0);

        LocalDateTime ldt = toLocalDateTime(c);

        Log.v("CALENDER", String.valueOf(c));
        Log.v("CALENDER", String.valueOf(ldt));
        Log.v("cek_repeat", String.valueOf(AlarmObject.repeat));
        Log.v("cek_date", String.valueOf(AlarmObject.date));


        if (c != null && c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        Log.v("cek_now", String.valueOf(Calendar.getInstance().getTimeInMillis()));
        Log.v("cek_alarm", String.valueOf(c.getTimeInMillis()));

//        Log.v("cek_time", String.valueOf(c.getTimeInMillis().))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            if (AlarmObject.date != null){
//                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
//                Log.v("alarm_type", "Single" );

                if (AlarmObject.repeat.equals("Everyday")){
//                    repeatInterval = 1000*60*60*24;
                    repeatInterval = 61000;

                    Log.v("pre_calendar", String.valueOf(c));

                    do {
                        if (c.before(Calendar.getInstance())) {
                            c.add(Calendar.DATE, 1);
                        }
                    } while (c.before(Calendar.getInstance()));

                    Log.v("post_calendar", String.valueOf(c));

                    Calendar alarm_time = Calendar.getInstance(TimeZone.getDefault());
                    Log.v("instance", String.valueOf(alarm_time));

//                    alarm_time.set(Calendar.MILLISECOND,0);
                    alarm_time.set(Calendar.HOUR_OF_DAY,2);
                    alarm_time.set(Calendar.MINUTE,50);
                    Log.v("modified_instance", String.valueOf(alarm_time));

                    long test = SystemClock.elapsedRealtime();
                    Log.v("system_clock", String.valueOf(test));



                    alarmManager.setRepeating
                            (AlarmManager.ELAPSED_REALTIME_WAKEUP, alarm_time.getTimeInMillis(),
                                    repeatInterval, pendingIntent);
                    Log.v("Alarm_type", "Daily" );

                } else { //Every particular day
                    repeatInterval = 1000*60*60*24*7;


                    Log.v("pre_calendar", String.valueOf(c));
                    do {
                        if (c.before(Calendar.getInstance())) {
                            c.add(Calendar.DAY_OF_WEEK, 1);
                        }
                    } while (c.before(Calendar.getInstance()));

                    Log.v("post_calendar", String.valueOf(c));

                    alarmManager.setRepeating
                            (AlarmManager.ELAPSED_REALTIME_WAKEUP, c.getTimeInMillis(),
                                    repeatInterval, pendingIntent);
                    Log.v("Alarm_type", "Weekly" );

                }

            } else {
//                if (AlarmObject.repeat == "Everyday"){
////                    repeatInterval = 1000*60*60*24;
//                    repeatInterval = 15000;
//                    alarmManager.setInexactRepeating
//                    (AlarmManager.ELAPSED_REALTIME_WAKEUP, c.getTimeInMillis() + repeatInterval,
//                            repeatInterval, pendingIntent);
//                    Log.v("Alarm_type", "Daily" );
//
//                } else { //Every particular day
//                    repeatInterval = 1000*60*60*24*7;
//                    alarmManager.setInexactRepeating
//                            (AlarmManager.ELAPSED_REALTIME_WAKEUP, c.getTimeInMillis() + repeatInterval,
//                                    repeatInterval, pendingIntent);
//                    Log.v("Alarm_type", "Weekly" );
//
//                }

                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
                Log.v("alarm_type", "Single" );

            }
//
        }
    }



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
