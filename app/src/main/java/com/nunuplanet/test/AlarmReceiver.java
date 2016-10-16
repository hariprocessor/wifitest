package com.nunuplanet.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.nunuplanet.test.communication.SendData;

import org.json.JSONException;

/**
 * Created by hari on 2016-10-16.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // For our recurring task, we'll just display a message
        Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
        try {
            new SendData(context).sendGPS();
            new SendData(context).sendWifi();
            new SendData(context).sendStep();
        } catch (JSONException e) {
            e.printStackTrace();

        }

    }
}