package com.example.tugas_besar_ifttw;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import org.w3c.dom.Text;


public class AddRoutineActivity extends AppCompatActivity {
    private Button addButton;
    private TabLayout tabLayout;
    private ViewPager addRoutinePager;
    private RadioGroup radioGroup;
    private RadioButton selectedAction;
    private Switch wifiSwitch;
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
        // Switch button of Wi-Fi: On or Off
        this.wifiSwitch = findViewById(R.id.wifi_switch_id);

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
        } else {
            switch(this.tabLayoutPosition) {
                case 0:
                    break;
                case 1:
                    this.validateNewsPage();
                    break;
            }
        }
    }

    private void validateNewsPage() {
        CharSequence newsKeyword = getNewsKeywordText();
        // If keyword is empty
        if (newsKeyword == null || newsKeyword.toString().isEmpty()) {
            Snackbar.make(addButton, "TextInput cannot be empty.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
        } else {
            if (this.getSelectedActionText().toString().equals("Send Me A Notification")) {
                // Handle "Send Me A Notification"
            } else {
                // Handle "Turn Wi-Fi"
                Log.v("WIFI", "Hello?");
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(this, NewsReceiver.class);

                int repeatInterval = 1000;
                int id = (int) SystemClock.elapsedRealtime();

                intent.putExtra("action", "Wifi");
                intent.putExtra("id", String.valueOf(id));

                Log.v("ID: ", String.valueOf(id));

                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + repeatInterval, repeatInterval, pendingIntent);

                Log.v("TAG", "ehehehe");

            }
        }
    }

    private CharSequence getNewsKeywordText() {
        TextInputEditText newsKeywordTextInput = findViewById(R.id.text_news_id);
        CharSequence newsKeyword = newsKeywordTextInput.getText();
        return newsKeyword;
    }

    private void setSelectedActionInThenSection () {
        int selectedId = this.radioGroup.getCheckedRadioButtonId();
        this.selectedAction = findViewById(selectedId);
    }

    private CharSequence getSelectedActionText() {
        return this.selectedAction.getText();
    }

    // Handle Actions
    private boolean isActionInThenSectionValid() {
        this.setSelectedActionInThenSection();
        if (this.selectedAction == null) {
            return false;
        }
        return true;
    }

    // Return true (ON) or false (OFF)
    private boolean getSwitchState() {
        return this.wifiSwitch.isChecked();
    }
}
