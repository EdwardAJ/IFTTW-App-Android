package com.example.tugas_besar_ifttw;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;

import static com.example.tugas_besar_ifttw.ControllerAlarmRoutine.startAlarmService;

public class FragmentTimer extends FragmentBaseAddRoutine implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    View view;
    private boolean isAttachedToRoot = false;
    private DialogFragment timePicker;
    private DialogFragment datePicker;
    private String selected_spinner;
    private Calendar c;
    private LocalDateTime ldt;
    private ZonedDateTime zdt;
    // Constructor
    public FragmentTimer() {
    }

    @Nullable
    @Override
    public View childOnCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timer, container, isAttachedToRoot);

        String [] values =
                {"Everyday","Every Monday","Every Tuesday","Every Wednesday","Every Thursday","Every Friday","Every Saturday","Every Sunday"};
        Spinner spinner = (Spinner) view.findViewById(R.id.text_repeat_id);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        return view;
    }

    @Override
    public void childAddButtonListener() {
        this.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddButtonOnClick();
            }
        });
    }

    // ValidatePSP
    protected void handleAddButtonOnClick() {
        this.isActionInThenSectionValid();
        this.validateTimerPage();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected_spinner = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), selected_spinner, Toast.LENGTH_SHORT).show();
//        Log.v("SPINNER", String.valueOf(selected_spinner));
    }

    @Override
    @TargetApi(26)
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        ldt = LocalDateTime.now();
        zdt = ldt.atZone(ZoneId.of("Asia/Ho_Chi_Minh"));

//        Log.v("TestHour", String.valueOf(hourOfDay));
//        Log.v("TestMinute", String.valueOf(minute));

        TextInputEditText tiet = getView().findViewById(R.id.text_choose_time_id);
        tiet.setText(hourOfDay + ":" + minute);
    }

    public void instantiateTimePicker(){
        timePicker = new FragmentTimePicker(this );
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

//        Log.v("TestYear", String.valueOf(year));
//        Log.v("TestMonth", String.valueOf(month));
//        Log.v("TestDayOfMonth", String.valueOf(dayOfMonth));

        TextInputEditText tiet = (TextInputEditText) getView().findViewById(R.id.text_date_id);
        tiet.setText(year + "/" + month + "/" + dayOfMonth);
    }

    public void instantiateDatePicker(){
        datePicker = new FragmentDatePicker(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextInputEditText tiet_time = (TextInputEditText) getView().findViewById(R.id.text_choose_time_id);
        tiet_time.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                instantiateTimePicker();
                timePicker.show(getActivity().getSupportFragmentManager(), "time picker");
            }
        });

        TextInputEditText tiet_date = (TextInputEditText) getView().findViewById(R.id.text_date_id);
        tiet_date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                instantiateDatePicker();
                datePicker.show(getActivity().getSupportFragmentManager(), "date picker");
            }
        });

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void validateTimerPage() {

        TextInputEditText tiet_time = getView().findViewById(R.id.text_choose_time_id);
        String[] separated_time_arr = tiet_time.getText().toString().split(":");

        TextInputEditText tiet_date = getView().findViewById(R.id.text_date_id);
        String string_tiet_date = tiet_date.getText().toString();

        if (separated_time_arr[0] == null || separated_time_arr[0] == null) {
            Snackbar.make(addButton, "Time input cannot be empty.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            Toast.makeText(getActivity(), "Service starts now...", Toast.LENGTH_LONG).show();
            int ID = (int) SystemClock.elapsedRealtime();

            ModelAlarm AlarmObj = new ModelAlarm(String.valueOf(ID),"Notification",Integer.parseInt(separated_time_arr[0]),Integer.parseInt(separated_time_arr[1]),string_tiet_date,selected_spinner);
            if (this.getSelectedActionText().equals("Send Me A Notification")) {
                AlarmObj.setNotifAttributes(this.notifTitle.getText().toString(), this.notifContent.getText().toString());
                this.createNotificationChannel();
            } else if (this.getSelectedActionText().equals("Turn Wifi On")){
                AlarmObj.action = "Wifi On";
                AlarmObj.setNotifAttributes("Wifi On", "Wifi Will be Turned On...");
            } else if (this.getSelectedActionText().equals("Turn Wifi Off")) {
                AlarmObj.action = "Wifi Off";
                AlarmObj.setNotifAttributes("Wifi Off", "Wifi Will be Turned Off...");
            }
            startAlarmService(getActivity(), AlarmObj, ID, c);
            saveAlarmToDatabase(AlarmObj);
            getActivity().finish();
        }
    }

    private void saveAlarmToDatabase(ModelAlarm AlarmObj) {
        database = new DatabaseHelper(getActivity());
        database.insertData(AlarmObj.modelID, AlarmObj.action, AlarmObj.notifTitle,
                AlarmObj.notifContent, "AlarmTimer", null, null, null,-1, AlarmObj.hour, AlarmObj.minute, AlarmObj.date,
                AlarmObj.repeat, 1);
    }
}