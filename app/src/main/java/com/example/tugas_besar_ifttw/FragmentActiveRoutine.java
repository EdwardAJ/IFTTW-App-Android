package com.example.tugas_besar_ifttw;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tugas_besar_ifttw.ControllerNewsRoutine;

import static com.example.tugas_besar_ifttw.ControllerNewsRoutine.cancelPendingIntent;
import static com.example.tugas_besar_ifttw.ControllerNewsRoutine.isPendingIntentRegistered;

public class FragmentActiveRoutine extends Fragment {
    View view;
    RelativeLayout fragmentRelativeLayout;
    DatabaseHelper database;
    private int isActive = 1;
    private boolean isAttachedToRoot = false;

    public FragmentActiveRoutine() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_active_routine, container, isAttachedToRoot);
        fragmentRelativeLayout = view.findViewById(R.id.relative_layout_active_fragment_id);

        database = new DatabaseHelper(getActivity());
        Cursor result = database.getAllDataWithIsActiveCondition(isActive);

//        AddRoutineActivity activity = new AddRoutineActivity();
//        Context context = activity.context;
        String prevRoutineID = null;
        if (result.getCount() != 0) {
            while (result.moveToNext()) {
                Log.v("ID: ", result.getString(0));
                String condName = null;
                String condValue = null;
                boolean isActive = false;

                // Construct baseObject
                ModelBase baseObject = new ModelBase(result.getString(0),
                        result.getString(1));
                baseObject.setNotifAttributes(result.getString(2),
                        result.getString(3));


                if (result.getString(4).equals("NewsAPI")) {
                    condName = result.getString(5);
                    condValue = result.getString(6);
                    ModelNews NewsObject = new ModelNews(baseObject.modelID, baseObject.action, condName, condValue);
                    NewsObject.setNotifAttributes(baseObject.notifTitle, baseObject.notifContent);

//                    cancelPendingIntent(getActivity(), NewsObject, Integer.parseInt(NewsObject.modelID));
                    isActive = isPendingIntentRegistered(getActivity(), NewsObject, Integer.parseInt(NewsObject.modelID));
                    Log.v("isActive? ", Boolean.toString(isActive));
                }

                if (isActive) {
                    // Construct new Relative Layout
                    ViewRoutine routine = new ViewRoutine(getActivity(), baseObject, condName, condValue);
                    if (prevRoutineID != null) {
                        routine.setRelativeLayoutBelow(prevRoutineID);
                    }
                    fragmentRelativeLayout.addView(routine.getRelativeLayout());
                    prevRoutineID = result.getString(0);
                }
            }
        }
        return view;
    }
}