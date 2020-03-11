package com.example.tugas_besar_ifttw;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentSensor extends Fragment {

    View view;
    private boolean isAttachedToRoot = false;

    // Constructor
    public FragmentSensor() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timer, container, isAttachedToRoot);
        return view;
    }
}
