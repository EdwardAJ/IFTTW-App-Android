package com.example.tugas_besar_ifttw;

public class ModelAlarm extends ModelBase {
    protected int hour;
    protected int minute;

    protected String date = null;
    protected String repeat = null;

    public ModelAlarm(String _modelID, String _action, int _hour, int _minute, String _date, String _repeat) {
        super(_modelID, _action);
        this.hour = _hour;
        this.minute = _minute;
        this.date = _date;
        this.repeat = _repeat;
    }
}
