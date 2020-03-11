package com.example.tugas_besar_ifttw;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import static android.content.Context.NOTIFICATION_SERVICE;

public abstract class FragmentBaseAddRoutine extends Fragment {
    View view;
    protected Button addButton;
    protected RadioGroup radioGroup;
    protected RadioButton selectedAction;
    protected TextInputEditText notifTitle;
    protected TextInputEditText notifContent;
    protected boolean isAttachedToRoot = false;

    DatabaseHelper database;

    protected NotificationManager mNotificationManager;
    protected static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    public FragmentBaseAddRoutine() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = childOnCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public abstract View childOnCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.addButton = getView().findViewById(R.id.add_button_id);
        // Radio buttons of then section: "Send Me A Notification" or "Turn Wi-Fi"
        this.radioGroup = getView().findViewById(R.id.radio_group_id);
        // Notification attribute
        this.notifTitle = getView().findViewById(R.id.text_notif_title_id);
        this.notifContent = getView().findViewById(R.id.text_notif_content_id);

        childAddButtonListener();
    }

    protected abstract void childAddButtonListener();

    protected void isActionInThenSectionValid() {
        if (!this.isActionSelected()) {
            Snackbar.make(addButton, "Action cannot be empty.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }

        if (this.getSelectedActionText().equals("Send Me A Notification")) {
            if (!this.isTitleAndContentValid()) {
                Snackbar.make(addButton, "Please fill the title or content.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
        }
    }

    protected boolean isTitleAndContentValid() {
        return this.isTitleValid() && this.isContentValid();
    }

    protected boolean isTitleValid() {
        return this.notifTitle.getText() != null && !this.notifTitle.getText().toString().isEmpty();
    }

    protected boolean isContentValid() {
        return this.notifContent.getText() != null && !this.notifContent.getText().toString().isEmpty();
    }

    protected String getSelectedActionText() {
        return this.selectedAction.getText().toString();
    }

    // Handle Actions
    protected boolean isActionSelected() {
        this.setSelectedActionInThenSection();
        if (this.selectedAction == null) {
            return false;
        }
        return true;
    }

    protected void setSelectedActionInThenSection () {
        int selectedId = this.radioGroup.getCheckedRadioButtonId();
        this.selectedAction = getView().findViewById(selectedId);
    }

    public void createNotificationChannel() {
        // Create a notification manager object.
        mNotificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel =
                    new NotificationChannel(PRIMARY_CHANNEL_ID, "Notification IFTTW",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifies every desired time");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
