package com.example.tugas_besar_ifttw;

public class ModelSensor extends ModelBase {
    protected double sensorValue;
    protected String sensorCompare;

    public ModelSensor(String _modelID, String _action, double _sensorValue, String sensorCompare) {
        super(_modelID, _action);
        this.sensorValue = _sensorValue;
        this.sensorCompare = sensorCompare;
    }
}
