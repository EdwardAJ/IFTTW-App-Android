package com.example.tugas_besar_ifttw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;


public class ViewRoutine  {
    private RelativeLayout relativeLayout;
    private LayoutInflater inf;
    private ModelBase ModelView;
    private String condName;
    private String condValue;

    public ViewRoutine(Context context, ModelBase ModelParam,
                       String _condName, String _condValue) {

        this.condName = _condName;
        this.condValue = _condValue;
        this.ModelView = new ModelBase(ModelParam.modelID, ModelParam.action);
        this.ModelView.setNotifAttributes(ModelParam.notifTitle, ModelParam.notifContent);

        relativeLayout = new RelativeLayout(context);
        relativeLayout.setId(Integer.parseInt(this.ModelView.modelID));
        relativeLayout.setBackgroundResource(R.color.colorRoutine);

        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        relativeLayout.setLayoutParams(rlp);

        inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = inf.inflate(R.layout.fragment_active_routine_content,null);

        TextView actionTextView = content.findViewById(R.id.text_view_action_id);
        actionTextView.setText(this.ModelView.action);

        TextView notifTitleTextView = content.findViewById(R.id.text_view_notif_title_id);
        notifTitleTextView.setText(this.ModelView.notifTitle);

        TextView notifContentTextView = content.findViewById(R.id.text_view_notif_content_id);
        notifContentTextView.setText(this.ModelView.notifContent);

        TextView condTextView = content.findViewById(R.id.text_view_condition_id);
        condTextView.setText(this.condName + " " + this.condValue);

        Switch routineSwitch = content.findViewById(R.id.routine_switch_id);
        routineSwitch.setChecked(true);

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
