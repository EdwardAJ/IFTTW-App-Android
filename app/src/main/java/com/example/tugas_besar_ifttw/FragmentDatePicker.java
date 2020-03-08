package com.example.tugas_besar_ifttw;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class FragmentDatePicker extends DialogFragment {

    FragmentDatePicker(DatePickerDialog.OnDateSetListener onDateSetListener){
        this.onDateSetListener = onDateSetListener;
    }
    DatePickerDialog.OnDateSetListener onDateSetListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        return new DatePickerDialog(getActivity(),onDateSetListener,year,month,dayOfMonth);
    }
}
