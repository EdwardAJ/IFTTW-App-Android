package com.example.tugas_besar_ifttw;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class FragmentTimePicker extends DialogFragment{

    FragmentTimePicker(TimePickerDialog.OnTimeSetListener onTimeSetListener){
        this.onTimeSetListener = onTimeSetListener;
    }
    TimePickerDialog.OnTimeSetListener onTimeSetListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(),onTimeSetListener,hour,minute,DateFormat.is24HourFormat(getActivity()));
    }
}

