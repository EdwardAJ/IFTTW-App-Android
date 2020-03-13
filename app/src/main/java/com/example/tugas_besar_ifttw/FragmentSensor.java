package com.example.tugas_besar_ifttw;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import static com.example.tugas_besar_ifttw.ControllerSensorRoutine.startSensorService;

public class FragmentSensor extends FragmentBaseAddRoutine {

    View view;

    @Override
    public View childOnCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sensor, container, isAttachedToRoot);
        return view;
    }

    @Override
    protected void childAddButtonListener() {
        this.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("CLICK", "CLICKEDDD");
                handleAddButtonOnClick();
            }
        });
    }

    protected String getLuminosityString() {
        TextInputEditText luminosity = getView().findViewById(R.id.text_choose_luminosity_id);
        return luminosity.getText().toString();
    }

    protected double getLuminosityDoubleValue() {
        return Double.parseDouble(getLuminosityString());
    }

    protected void handleAddButtonOnClick() {
        this.isActionInThenSectionValid();
        this.validateSensor();
    }

    protected void validateSensor() {
        if (getLuminosityString().isEmpty()) {
            Snackbar.make(addButton, "Luminosity cannot be empty.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            Toast.makeText(getActivity(), "Service starts now...", Toast.LENGTH_LONG).show();
            int ID = (int) SystemClock.elapsedRealtime();
            ModelSensor SensorObject = new ModelSensor(String.valueOf(ID), "Notification",getLuminosityDoubleValue(), "MORE_THAN");
            if (this.getSelectedActionText().equals("Send Me A Notification")) {
                SensorObject.setNotifAttributes(this.notifTitle.getText().toString(), this.notifContent.getText().toString());
                this.createNotificationChannel();
            } else if (this.getSelectedActionText().equals("Turn Wifi On")) {
                SensorObject.action = "Wifi On";
                SensorObject.setNotifAttributes("Wifi On", "Wifi Will be Turned On...");
            } else if (this.getSelectedActionText().equals("Turn Wifi Off")) {
                SensorObject.action = "Wifi Off";
                SensorObject.setNotifAttributes("Wifi Off", "Wifi Will be Turned Off...");
            }

            saveSensorToDatabase(SensorObject);
            ControllerSensorRoutine.stopService(getActivity().getApplicationContext());
            ControllerSensorRoutine.startSensorService(getActivity().getApplicationContext());
            getActivity().finish();
        }
    }

    protected void saveSensorToDatabase(ModelSensor sensorObject) {
        database = new DatabaseHelper(getActivity());
        database.insertData(sensorObject.modelID, sensorObject.action, sensorObject.notifTitle,
                sensorObject.notifContent, "Sensor", null,
                null, "MORE_THAN", getLuminosityDoubleValue(), -1, -1, null,
                null, 1);
    }
}
