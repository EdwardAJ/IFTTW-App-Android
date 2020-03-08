package com.example.tugas_besar_ifttw;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class AddRoutineActivity extends AppCompatActivity {
    private NotificationManager mNotificationManager;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    private Button addButton;
    private TabLayout tabLayout;
    private ViewPager addRoutinePager;
    private RadioGroup radioGroup;
    private RadioButton selectedAction;
    private TextInputEditText notifTitle;
    private TextInputEditText notifContent;

    // tabLayoutPosition: 0 for Timer, 1 for News and 2 for Sensor
    private int tabLayoutPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_routine);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.tabLayout = findViewById(R.id.tabLayout);
        // For fragments
        this.addRoutinePager = findViewById(R.id.viewPager);
        this.addButton = findViewById(R.id.add_button_id);
        // Radio buttons of then section: "Send Me A Notification" or "Turn Wi-Fi"
        this.radioGroup = findViewById(R.id.radio_group_id);
        // Notification attribute
        this.notifTitle = findViewById(R.id.text_notif_title_id);
        this.notifContent = findViewById(R.id.text_notif_content_id);

        // Apply fragment from fragment_news.xml and fragment_timer.xml
        AddRoutinePagerAdapter adapter = new AddRoutinePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentTimer(), "Timer");
        adapter.addFragment(new FragmentNews(), "News");

        this.addRoutinePager.setAdapter(adapter);
        this.tabLayout.setupWithViewPager(addRoutinePager);

        // Get which TabLayout page on click
        this.addTabLayoutListener();
        this.addButtonListener();
    }

    private void addTabLayoutListener() {
        this.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabLayoutPosition = tab.getPosition();
                Log.v("POSITION", String.valueOf(tabLayoutPosition));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void addButtonListener() {
        this.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("CLICK", "CLICKEDDD");
                handleAddButtonOnClick();
            }
        });
    }

    // Validate: Timer, News and Sensor
    private void handleAddButtonOnClick() {
        if (!this.isActionInThenSectionValid()) {
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

        switch(this.tabLayoutPosition) {
            case 0:
                break;
            case 1:
                this.validateNewsPage();
                break;
            }
    }

    private boolean isTitleAndContentValid() {
        return this.isTitleValid() && this.isContentValid();
    }

    private boolean isTitleValid() {
        return this.notifTitle.getText() != null && !this.notifTitle.getText().toString().isEmpty();
    }

    private boolean isContentValid() {
        return this.notifContent.getText() != null && !this.notifContent.getText().toString().isEmpty();
    }

    private void validateNewsPage() {
        CharSequence newsKeyword = getNewsKeywordText();
        // If keyword is empty
        if (newsKeyword == null || newsKeyword.toString().isEmpty()) {
            Snackbar.make(addButton, "TextInput cannot be empty.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
        } else {
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent = new Intent(this, NewsReceiver.class);
            int repeatInterval = 10000;
            int id = (int) SystemClock.elapsedRealtime();

            intent.putExtra("id", String.valueOf(id));
            intent.putExtra("newsKeyword", newsKeyword.toString());
            intent.putExtra("newsTimeFrom", getCurrentDateISO());

            if (this.getSelectedActionText().equals("Send Me A Notification")) {
                // Handle "Send Me A Notification"
                intent.putExtra("action", "Notification");
                intent.putExtra("notifTitle", this.notifTitle.getText().toString());
                intent.putExtra("notifContent", this.notifContent.getText().toString());
                this.createNotificationChannel();
            } else {
                Log.v("WIFI: ", "WIFI?");
                // Handle "Turn Wi-Fi"
                intent.putExtra("action", "Wifi");
            }

            Log.v("ID: ", String.valueOf(id));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + repeatInterval, repeatInterval, pendingIntent);

            // Remove old alarm
            alarmManager.cancel(pendingIntent);
            // Set new alarm
            alarmManager.set(AlarmManager.RTC_WAKEUP, repeatInterval, pendingIntent);
        }
    }

    private CharSequence getNewsKeywordText() {
        TextInputEditText newsKeywordTextInput = findViewById(R.id.text_news_id);
        CharSequence newsKeyword = newsKeywordTextInput.getText();
        return newsKeyword;
    }

    private String getSelectedActionText() {
        return this.selectedAction.getText().toString();
    }

    // Handle Actions
    private boolean isActionInThenSectionValid() {
        this.setSelectedActionInThenSection();
        if (this.selectedAction == null) {
            return false;
        }
        return true;
    }

    private void setSelectedActionInThenSection () {
        int selectedId = this.radioGroup.getCheckedRadioButtonId();
        this.selectedAction = findViewById(selectedId);
    }

    public String getCurrentDateISO () {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz);
        return df.format(new Date());
    }

    public void createNotificationChannel() {
        // Create a notification manager object.
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                            "Notification IFTTW",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    ("Notifies every desired time");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
