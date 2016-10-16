package com.nunuplanet.test;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.nunuplanet.test.communication.SendData;
import com.nunuplanet.test.communication.SendGPS;
import com.nunuplanet.test.database.GPS;
import com.nunuplanet.test.database.Steps;
import com.nunuplanet.test.database.WiFiList;
import com.nunuplanet.test.database.WiFiTools;
import com.nunuplanet.test.wifi.WiFiData;
import com.nunuplanet.test.wifi.WifiListAdapter;

import org.json.JSONException;

import java.sql.Time;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity {
    public final static String URL = "http://166.104.231.227:8080/gpstest";


    private ScanResult scanResult;
    private WifiManager wifiManager;
    private List<ScanResult> apList;
    private IntentFilter intentFilter;
    private WifiListAdapter wifiListAdapter = new WifiListAdapter();
    private ListView wifiListView;

    private Button sendGpsButton;
    private Button sendWifiButton;
    private Button sendStepButton;

    private SensorManager sensorManager;
    private Sensor sensor;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(sensor != null){
            sensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    StepsTools.saveSteps(TimeStamp.getTimeStamp());
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            }, sensor, SensorManager.SENSOR_DELAY_UI);
        }
        else{
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_SHORT).show();
        }
*/
        sendGpsButton = (Button) findViewById(R.id.send_gps_button);
        sendWifiButton = (Button) findViewById(R.id.send_wifi_button);
        sendStepButton = (Button) findViewById(R.id.send_step_button);
        sendGpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new SendData(getApplicationContext()).sendGPS();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        sendWifiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new SendData(getApplicationContext()).sendWifi();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        sendStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new SendData(getApplicationContext()).sendStep();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        // get permissions
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        // initialize realm
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm realm = Realm.getInstance(realmConfiguration);

        // test
        RealmResults<WiFiList> query = realm.where(WiFiList.class).findAll();
        Log.i("hari size wifi", String.valueOf(query.size()));
        RealmResults<GPS> query2 = realm.where(GPS.class).findAll();
        Log.i("hari size gps", String.valueOf(query2.size()));
        /*
        for(int i = 0; i < query.size(); i++){
            Log.i("hari", String.valueOf(query.get(i).getBSSID())+", "+String.valueOf(query.get(i).getTimeStamp()));
        }
        */
/*
        // activate gps
        GetGPS getGPS = new GetGPS(this);
        getGPS.getGPS();

        // activate wifi
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiListView = (ListView) findViewById(R.id.wifi_list_view);
        scan();

*/
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

/*
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        startService(intent);
*/
        Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);


        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000*20;
        Log.i("hari timestamp", String.valueOf(TimeStamp.getTriggerTimeStamp()));
        Log.i("hari timestamp2", String.valueOf(System.currentTimeMillis()));
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, TimeStamp.getTriggerTimeStamp(), interval, pendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();

    }

    private PendingIntent pendingIntent;

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000) {

            /* 요청한 권한을 사용자가 "허용"했다면 인텐트를 띄워라
                내가 요청한 게 하나밖에 없기 때문에. 원래 같으면 for문을 돈다.*/
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "permission granted well :D", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "권한 요청을 거부했습니다.", Toast.LENGTH_SHORT).show();
            }

        }
    }


    public void scan() {
        wifiManager.startScan();
        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        getApplicationContext().registerReceiver(wifiReceiver, intentFilter);
        //Log.i("hari ", "scan");
    }

    private BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                searchWifi();
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.M)
    public void searchWifi() {
        /*
        wifiListView.setAdapter(wifiListAdapter);
        */
        apList = wifiManager.getScanResults();

        if (wifiManager.getScanResults() != null) {

            int size = apList.size();

            //wifiListAdapter.clear();
            for (int i = 0; i < size; i++) {

                WiFiData wifiData = new WiFiData();
                wifiData.BSSID = apList.get(i).BSSID;
                wifiData.SSID = apList.get(i).SSID;
                wifiData.level = apList.get(i).level;
                wifiData.venueName = String.valueOf(apList.get(i).venueName);
                //wifiListAdapter.add(wifiData);

                WiFiTools.saveWiFi(wifiData.BSSID, wifiData.SSID, wifiData.level, TimeStamp.getTimeStamp());
            }
            //wifiListAdapter.sort();
            //wifiListAdapter.notifyDataSetChanged();
        }
        scan();
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.nunuplanet.test/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.nunuplanet.test/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
