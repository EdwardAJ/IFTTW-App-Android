package com.example.tugas_besar_ifttw;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class FragmentTimer extends Fragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
        Log.v("SPINNER", String.valueOf(text));
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

//        Spinner repeat_array = (Spinner) getView().findViewById(R.id.text_repeat_id);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.repeats_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        repeat_array.setAdapter(adapter);
//        repeat_array.setOnItemSelectedListener(this);

//        repeat_array.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//            }
//        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}