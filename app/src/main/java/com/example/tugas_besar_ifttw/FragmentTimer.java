package com.example.tugas_besar_ifttw;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class FragmentTimer extends Fragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    View view;
    private boolean isAttachedToRoot = false;
    private DialogFragment timePicker;
    private DialogFragment datePicker;

    // Constructor
    public FragmentTimer() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timer, container, isAttachedToRoot);
        return view;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        Log.v("TestHour", String.valueOf(hourOfDay));
        Log.v("TestMinute", String.valueOf(minute));

        TextInputEditText tiet = (TextInputEditText) getView().findViewById(R.id.text_choose_time_id);
        tiet.setText(hourOfDay + " : " + minute);
    }

    public void instantiateTimePicker(){
        timePicker = new FragmentTimePicker(this );
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        Log.v("TestYear", String.valueOf(year));
        Log.v("TestMonth", String.valueOf(month));
        Log.v("TestDayOfMonth", String.valueOf(dayOfMonth));

        TextInputEditText tiet = (TextInputEditText) getView().findViewById(R.id.text_date_id);
        tiet.setText(year + " / " + month + " / " + dayOfMonth);
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
}