package com.example.tugas_besar_ifttw;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class NewsReceiver extends BroadcastReceiver {

    private RequestQueue queue;
    private int totalResults;
    private String action;
    private String id;
    private String newsKeyword;
    private String newsTimeFrom;
    private WifiManager wifiManager;

    private String notifTitle;
    private String notifContent;
    private NotificationManager mNotificationManager;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    @Override
    public void onReceive(final Context context, Intent intent) {
        wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        action = intent.getStringExtra("action");
        id = intent.getStringExtra("id");
        newsKeyword = intent.getStringExtra("newsKeyword");
        newsTimeFrom = intent.getStringExtra("newsTimeFrom");

        notifTitle = intent.getStringExtra("notifTitle");
        notifContent = intent.getStringExtra("notifContent");

        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Log.v("Time", newsTimeFrom);
        this.startAPIRequest(context);

    }

    public void startAPIRequest(final Context context) {
        String url = "https://newsapi.org/v2/everything?q=" + newsKeyword + "&from=" + newsTimeFrom + "&sortBy=publishedAt&apiKey=f44d6a62d0b944ff94f0de1954533238";
        Log.v("URL", url);
        queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.v("RESPONSE:", response.toString());
                            String tempTotalResults = response.getString("totalResults");
                            Log.v("RESPONSE2: ", tempTotalResults);

                            totalResults = Integer.parseInt(tempTotalResults);
                            // TODO: change to totalResults > 0
                            if (totalResults == 0) {
                                Log.v("ACTION", action);
                                if (action.equals("Wifi")) {
                                    changeWifiState(context);
                                } else {
                                    // TODO: make notification
                                    deliverNotification(context);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("Error", error.toString());
                        deliverNotification(context);
                        Toast.makeText(context, error.toString(), 5000).show();
                    }
                });
        queue.add(jsonObjectRequest);
    }

    public void changeWifiState(final Context context) {
        Toast.makeText(context, "Wifi Status Is Changed!", 5000).show();
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
            Log.v("State", "TRUE");
        } else {
            Log.v("State", "FALSE");
            wifiManager.setWifiEnabled(false);
        }
    }

    private void deliverNotification(Context context) {
        Intent contentIntent = new Intent(context, AddRoutineActivity.class);

        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, Integer.parseInt(this.id), contentIntent, PendingIntent
                        .FLAG_UPDATE_CURRENT);
        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder
                (context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(this.notifTitle)
                .setContentText(this.notifContent)
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        // Deliver the notification
        mNotificationManager.notify(Integer.parseInt(this.id), builder.build());
    }

}
