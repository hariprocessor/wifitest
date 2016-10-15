package com.nunuplanet.test;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import com.nunuplanet.test.database.WiFiTools;
import com.nunuplanet.test.wifi.WiFiData;
import com.nunuplanet.test.wifi.WiFiManager;

/**
 * Created by hari on 2016-10-16.
 */
public class MyService extends android.app.Service{


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ori)
                .setContentTitle("HARI")
                .setContentText("GPS WiFi StepCount");

        notificationManager.notify(1, builder.build());

        WiFiManager wiFiManager = new WiFiManager(getApplicationContext());
        wiFiManager.scan();

        GetGPS getGPS = new GetGPS(getApplicationContext());
        getGPS.getGPS();

        GetSteps getSteps = new GetSteps(getApplicationContext());
        getSteps.step();
        return START_STICKY;
    }

    public void onDestroy(){
        Toast.makeText(this, "HARI the end..", Toast.LENGTH_LONG).show();
    }



}
