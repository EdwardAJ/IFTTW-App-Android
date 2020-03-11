package com.example.tugas_besar_ifttw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.tugas_besar_ifttw.ControllerNewsRoutine.cancelPendingIntent;
import static com.example.tugas_besar_ifttw.ControllerNewsRoutine.startNewsService;
import com.example.tugas_besar_ifttw.DatabaseHelper;

public class ViewRoutine  {
    private RelativeLayout relativeLayout;
    private LayoutInflater inf;
    private ModelBase ModelView;
    private String condName;
    private String condValue;

    private Switch routineSwitch;
    private Button deleteButton;
    private View content;

    DatabaseHelper database;

    public ViewRoutine(Context context, ModelBase ModelParam,
                       String _condName, String _condValue) {

        this.condName = _condName;
        this.condValue = _condValue;
        this.ModelView = new ModelBase(ModelParam.modelID, ModelParam.action);
        this.ModelView.setNotifAttributes(ModelParam.notifTitle, ModelParam.notifContent);

        relativeLayout = new RelativeLayout(context);
        relativeLayout.setId(Integer.parseInt(this.ModelView.modelID));
        relativeLayout.setBackgroundResource(R.color.colorRoutine);

        inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        content = inf.inflate(R.layout.fragment_active_routine_content,null);

        routineSwitch = content.findViewById(R.id.routine_switch_id);
        routineSwitch.setChecked(true);

        deleteButton = content.findViewById(R.id.delete_button_id);

        database = new DatabaseHelper(context);
    }

    public void addSwitchNewsListener(final Context context, final ModelNews NewsObject, final int repeatInterval, final int ID ) {
        routineSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(context, "Starting Routine...", Toast.LENGTH_SHORT).show();
                    startNewsService(context, NewsObject, repeatInterval, ID);
                    database.updateIsActiveData(String.valueOf(ID), 1);
                } else {
                    Toast.makeText(context, "Cancelling Routine...", Toast.LENGTH_SHORT).show();
                    cancelPendingIntent(context, NewsObject, ID);
                    database.updateIsActiveData(String.valueOf(ID), 0);
                }
            }
        });
    }

    public void addDeleteButtonNewsListener(final Context context, final ModelNews NewsObject, final int ID) {
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Deleting Routine...", Toast.LENGTH_SHORT).show();
                cancelPendingIntent(context, NewsObject, ID);
                database.deleteData(String.valueOf(ID));
            }
        });
    }

    public void constructView() {
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        relativeLayout.setLayoutParams(rlp);

        TextView actionTextView = content.findViewById(R.id.text_view_action_id);
        actionTextView.setText(this.ModelView.action);

        TextView notifTitleTextView = content.findViewById(R.id.text_view_notif_title_id);
        notifTitleTextView.setText(this.ModelView.notifTitle);

        TextView notifContentTextView = content.findViewById(R.id.text_view_notif_content_id);
        notifContentTextView.setText(this.ModelView.notifContent);

        TextView condTextView = content.findViewById(R.id.text_view_condition_id);
        condTextView.setText(this.condName + " " + this.condValue);

        relativeLayout.addView(content);
    }

    public RelativeLayout getRelativeLayout() {
        return relativeLayout;
    }

    public void setRelativeLayoutBelow(String prevRelativeLayoutID) {
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.BELOW, Integer.parseInt(prevRelativeLayoutID));
        this.getRelativeLayout().setLayoutParams(rlp);
    }
}