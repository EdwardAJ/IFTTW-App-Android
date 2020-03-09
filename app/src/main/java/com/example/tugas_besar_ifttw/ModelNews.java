package com.example.tugas_besar_ifttw;

import android.os.Parcel;

public class ModelNews extends ModelBase {

    protected String newsKeyword;
    protected String newsTimeFrom;

    public ModelNews(String _modelID, String _action, String _newsKeyword, String _newsTimeFrom) {
        super(_modelID, _action);
        this.newsKeyword = _newsKeyword;
        this.newsTimeFrom = _newsTimeFrom;
    }
}
