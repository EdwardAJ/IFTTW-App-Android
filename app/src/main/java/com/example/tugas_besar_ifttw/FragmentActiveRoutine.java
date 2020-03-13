package com.example.tugas_besar_ifttw;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

import static com.example.tugas_besar_ifttw.ControllerAlarmRoutine.convertStringToCalendar;

public class FragmentActiveRoutine extends Fragment {
    View view;
    RelativeLayout fragmentRelativeLayout;
    DatabaseHelper database;
    private int isActive = 1;
    private boolean isAttachedToRoot = false;

    public FragmentActiveRoutine() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_active_routine, container, isAttachedToRoot);
        fragmentRelativeLayout = view.findViewById(R.id.relative_layout_active_fragment_id);

        database = new DatabaseHelper(getActivity());
        Cursor result = database.getAllDataWithIsActiveCondition(isActive);

        String prevRoutineID = null;
        if (result.getCount() != 0) {
            while (result.moveToNext()) {
                Log.v("ID: ", result.getString(0));
                Log.v("Result", result.getString(4));
                String condName = null;
                String condValue = null;

                // Construct baseObject
                ModelBase baseObject = new ModelBase(result.getString(0),
                        result.getString(1));
                baseObject.setNotifAttributes(result.getString(2),
                        result.getString(3));


                if (result.getString(4).equals("NewsAPI")) {
                    condName = result.getString(5);
                    condValue = result.getString(6);
                    ModelNews NewsObject = new ModelNews(baseObject.modelID, baseObject.action, condName, condValue);
                    NewsObject.setNotifAttributes(baseObject.notifTitle, baseObject.notifContent);

                        // Construct new Relative Layout
                        ViewRoutine routine = new ViewRoutine(getActivity(), baseObject, condName, condValue, isActive);
                        routine.addDeleteButtonNewsListener(getActivity().getApplicationContext(), NewsObject, Integer.parseInt(NewsObject.modelID));
                        routine.addSwitchNewsListener(getActivity().getApplicationContext(), NewsObject, 5000, Integer.parseInt(NewsObject.modelID));
                        routine.constructView();
                        if (prevRoutineID != null) {
                            routine.setRelativeLayoutBelow(prevRoutineID);
                        }
                        fragmentRelativeLayout.addView(routine.getRelativeLayout());
                        prevRoutineID = result.getString(0);

                } else if (result.getString(4).equals("Sensor")) {
                    condName = result.getString(7);
                    Double sensorCondValue = result.getDouble(8);
                    ModelSensor SensorObject = new ModelSensor(baseObject.modelID, baseObject.action, sensorCondValue, condName);
                    SensorObject.setNotifAttributes(baseObject.notifTitle, baseObject.notifContent);

                    // Construct new Relative Layout
                    ViewRoutine routine = new ViewRoutine(getActivity(), baseObject, condName, String.valueOf(sensorCondValue), isActive);
                    routine.addDeleteButtonSensorListener(getActivity().getApplicationContext(), Integer.parseInt(SensorObject.modelID));
                    routine.addSwitchSensorListener(getActivity().getApplicationContext(), Integer.parseInt(SensorObject.modelID));
                    routine.constructView();

                    if (prevRoutineID != null) {
                        routine.setRelativeLayoutBelow(prevRoutineID);
                    }
                    fragmentRelativeLayout.addView(routine.getRelativeLayout());
                    prevRoutineID = result.getString(0);

                } else if (result.getString(4).equals("AlarmTimer")) {
                    int timerHour = result.getInt(9);
                    int timerMinute = result.getInt(10);
                    String timerDate = result.getString(11);
                    String timerRepeat = result.getString(12);

                    if (timerDate != null && !timerDate.isEmpty()) {
                        condValue = timerDate;
                    } else if (timerRepeat != null && !timerRepeat.isEmpty()) {
                        condValue = timerRepeat;
                    }

                    condName = String.valueOf(timerHour) + ":" + String.valueOf(timerMinute);
                    ModelAlarm AlarmObject = new ModelAlarm(baseObject.modelID, baseObject.action, timerHour, timerMinute, timerDate, timerRepeat);
                    AlarmObject.setNotifAttributes(baseObject.notifTitle, baseObject.notifContent);
                    ViewRoutine routine = new ViewRoutine(getActivity().getApplicationContext(), baseObject, condName, condValue, isActive);

                    Calendar calendar;
                    if (timerDate != null && !timerDate.isEmpty()) {
                        calendar = ControllerAlarmRoutine.convertStringToCalendar(timerDate, timerHour, timerMinute);
                    } else {
                        calendar = ControllerAlarmRoutine.convertStringToTodayCalendar(timerHour, timerMinute);
                    }

                    routine.addSwitchAlarmListener(getActivity().getApplicationContext(), AlarmObject, calendar, Integer.parseInt(AlarmObject.modelID));
                    routine.addDeleteButtonAlarmListener(getActivity().getApplicationContext(), AlarmObject, Integer.parseInt(AlarmObject.modelID));
                    routine.constructView();

                    if (prevRoutineID != null) {
                        routine.setRelativeLayoutBelow(prevRoutineID);
                    }
                    fragmentRelativeLayout.addView(routine.getRelativeLayout());
                    prevRoutineID = result.getString(0);
                }
            }
        }
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

}