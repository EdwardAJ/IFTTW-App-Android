package com.example.tugas_besar_ifttw;

public abstract class ModelBase {
    protected String modelID;
    protected String action;
    protected String notifContent;
    protected String notifTitle;

    public ModelBase(String _modelID, String _action) {
        this.modelID = _modelID;
        this.action = _action;
    }

    public void setNotifAttributes(String _notifContent, String _notifTitle) {
        this.notifContent = _notifContent;
        this.notifTitle = _notifTitle;
    }
}
