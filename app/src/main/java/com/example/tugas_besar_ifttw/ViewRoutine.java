package com.example.tugas_besar_ifttw;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Switch;

class Condition {
    private static String condName;
    private static String condContent;

    // Example of condContentValue:
    // Timer: Time and Repeat, (and Date)
    // News: Keyword
    // Sensor: Sensor Value
    private static String condContentValue;
}

public class ViewRoutine extends Activity {
    private int routineID;
    private String actionName;
    private Switch toggleActiveRoutine;
    private Condition condition;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create a new layout
        RelativeLayout relativeLayout = new RelativeLayout(this);
        // Width: Match_Parent, Height: Wrap_Content
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        // Create internal components
    }
}
