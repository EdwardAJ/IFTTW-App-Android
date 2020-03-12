package com.example.tugas_besar_ifttw;

public class ModelBase {
    protected String modelID;
    protected String action;
    protected String notifContent = null;
    protected String notifTitle = null;

    public ModelBase(String _modelID, String _action) {
        this.modelID = _modelID;
        this.action = _action;
    }

    public void setNotifAttributes(String _notifTitle, String _notifContent) {
        this.notifContent = _notifContent;
        this.notifTitle = _notifTitle;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
