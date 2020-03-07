package com.example.tugas_besar_ifttw;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;


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
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
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
        Log.v("POSITION: ", String.valueOf(this.tabLayoutPosition));
        if (this.tabLayoutPosition == 0) {

        } else if (this.tabLayoutPosition == 1) {
            this.validateNewsPage();
        }
    }

    private void validateNewsPage() {
        TextInputEditText newsKeywordTextInput = findViewById(R.id.text_news_id);
        CharSequence newsKeyword = newsKeywordTextInput.getText();
        // If keyword is empty
        if (newsKeyword == null || newsKeyword.toString().isEmpty()) {
            // Show snackbar
            Snackbar.make(addButton, "TextInput cannot be empty.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            this.validateActionInThenSection();
        }
    }

    private void setSelectedActionInThenSection () {
        int selectedId = this.radioGroup.getCheckedRadioButtonId();
        this.selectedAction = findViewById(selectedId);
    }

    // Handle "Then" values
    private void validateActionInThenSection() {
        this.setSelectedActionInThenSection();
        if (this.selectedAction == null) {
            Snackbar.make(addButton, "Action cannot be empty.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            // Handle action: Send Me a Notification and Turn Wi-Fi:
            this.setSelectedActionInThenSection();
            CharSequence selectedActionText = this.selectedAction.getText();
            if (selectedActionText.toString().equals("Send Me A Notification")) {
                // Handle "Send Me A Notification" Action
            } else {
                // Handle "Turn Wi-Fi"
            }
        }
    }
}
