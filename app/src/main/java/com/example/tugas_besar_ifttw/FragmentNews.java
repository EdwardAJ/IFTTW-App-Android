package com.example.tugas_besar_ifttw;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static com.example.tugas_besar_ifttw.ControllerNewsRoutine.startNewsService;

public class FragmentNews extends FragmentBaseAddRoutine {

    @Override
    public View childOnCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, isAttachedToRoot);
        return view;
    }

    @Override
    public void childAddButtonListener() {
        this.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("CLICK", "CLICKEDDD");
                handleAddButtonOnClick();
            }
        });
    }

    // Validate
    protected void handleAddButtonOnClick() {
        this.isActionInThenSectionValid();
        this.validateNewsPage();
    }

    private void validateNewsPage() {
        CharSequence newsKeyword = getNewsKeywordText();
        if (newsKeyword == null || newsKeyword.toString().isEmpty()) {
            Snackbar.make(addButton, "TextInput cannot be empty.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            Toast.makeText(getActivity(), "Service starts now...", Toast.LENGTH_LONG).show();
            int ID = (int) SystemClock.elapsedRealtime();
            ModelNews NewsObject = new ModelNews(String.valueOf(ID), "Notification", newsKeyword.toString(), getCurrentDateISO());
            if (this.getSelectedActionText().equals("Send Me A Notification")) {
                NewsObject.setNotifAttributes(this.notifTitle.getText().toString(), this.notifContent.getText().toString());
                this.createNotificationChannel();
            } else if (this.getSelectedActionText().equals("Turn Wifi On")) {
                NewsObject.action = "Wifi On";
                NewsObject.setNotifAttributes("Wifi On", "Wifi Will be Turned On...");
            } else if (this.getSelectedActionText().equals("Turn Wifi Off")) {
                NewsObject.action = "Wifi Off";
                NewsObject.setNotifAttributes("Wifi Off", "Wifi Will be Turned Off...");
            }

            int repeatInterval = 5000; // 5s
            startNewsService(getActivity().getApplicationContext(), NewsObject, repeatInterval, ID);
            saveNewsToDatabase(NewsObject);
            getActivity().finish();
        }
    }


    private void saveNewsToDatabase(ModelNews NewsObject) {
        database = new DatabaseHelper(getActivity());
        database.insertData(NewsObject.modelID, NewsObject.action, NewsObject.notifTitle,
                NewsObject.notifContent, "NewsAPI", NewsObject.newsKeyword,
                NewsObject.newsTimeFrom, null,-1, -1, -1, null,
                null, 1);
    }

    private CharSequence getNewsKeywordText() {
        TextInputEditText newsKeywordTextInput = getView().findViewById(R.id.text_news_id);
        CharSequence newsKeyword = newsKeywordTextInput.getText();
        return newsKeyword;
    }


    public String getCurrentDateISO () {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz);
        return df.format(new Date());
    }

}
