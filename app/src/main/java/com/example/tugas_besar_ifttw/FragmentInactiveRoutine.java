package com.example.tugas_besar_ifttw;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class FragmentInactiveRoutine extends Fragment {
    View view;
    RelativeLayout fragmentRelativeLayout;
    DatabaseHelper database;
    private int isActive = 0;
    private boolean isAttachedToRoot = false;

    public FragmentInactiveRoutine() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_active_routine, container, isAttachedToRoot);
        fragmentRelativeLayout = view.findViewById(R.id.relative_layout_active_fragment_id);

        database = new DatabaseHelper(getActivity());
        Cursor result = database.getAllDataWithIsActiveCondition(isActive);

        String prevRoutineID = null;
        if (result.getCount() != 0) {
            while (result.moveToNext()) {
                Log.v("ID: ", result.getString(0));
                String condName = null;
                String condValue = null;
//                boolean isActive = false;

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

                    // cancelPendingIntent(getActivity().getApplicationContext(), NewsObject, Integer.parseInt(NewsObject.modelID));
//                    isActive = isPendingIntentRegistered(getActivity().getApplicationContext(), NewsObject, Integer.parseInt(NewsObject.modelID));
//                    Log.v("isActive? ", Boolean.toString(isActive));


                        // Construct new Relative Layout
                        ViewRoutine routine = new ViewRoutine(getActivity(), baseObject, condName, condValue, isActive);
                        routine.addDeleteButtonNewsListener(getActivity().getApplicationContext(), NewsObject, Integer.parseInt(NewsObject.modelID));
                        routine.addSwitchNewsListener(getActivity().getApplicationContext(), NewsObject, 5000, Integer.parseInt(NewsObject.modelID));
                        routine.constructView();
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
}