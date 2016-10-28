package com.nunuplanet.test;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.nunuplanet.test.communication.SendData;
import com.nunuplanet.test.wifi.WiFiManager;

import org.json.JSONException;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by hari on 2016-10-16.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Realm.init(context);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm realm = Realm.getInstance(realmConfiguration);

        // For our recurring task, we'll just display a message
        Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
        try {
            new SendData(context).sendGPS();
            new SendData(context).sendWifi();
            new SendData(context).sendStep();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ori)
                .setContentTitle("HARI")
                .setContentText("GPS WiFi StepCount");

        notificationManager.notify(1, builder.build());

        WiFiManager wiFiManager = new WiFiManager(context);
        wiFiManager.scan();

        GetGPS getGPS = new GetGPS(context);
        getGPS.getGPS();

        GetSteps getSteps = new GetSteps(context);
        getSteps.step();

    }
}