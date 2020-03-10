package com.example.tugas_besar_ifttw;

import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

public class FragmentSensor extends Fragment implements SensorEventListener {
    View view;
    private boolean isAttachedToRoot = false;

    SensorManager sensorManager;
    Sensor sensor;

    TextInputEditText tiet_sensor;

    // Constructor
    public FragmentSensor() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sensor, container, isAttachedToRoot);
//
//            sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
//            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sensorManager = (SensorManager) getActivity().getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

//        tiet_sensor = (TextInputEditText) getView().findViewById(R.id.text_choose_luminosity_id);
//        tiet_sensor.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
////                instantiateDatePicker();
//                tiet_sensor.show(getActivity().getSupportFragmentManager(), "lumen picker");
//            }
//        });
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_LIGHT){
            Log.v("Sensor", String.valueOf(event));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
