package com.nunuplanet.test;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.nunuplanet.test.communication.SendGPS;
import com.nunuplanet.test.database.GPSTools;
import com.nunuplanet.test.wifi.WiFiData;
import com.nunuplanet.test.wifi.WifiListAdapter;

import org.json.JSONException;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public final static String URL = "http://localhost:8080/gpstest";


    private ScanResult scanResult;
    private WifiManager wifiManager;
    private List<ScanResult> apList;
    private IntentFilter intentFilter;
    private WifiListAdapter wifiListAdapter = new WifiListAdapter();
    private ListView wifiListView;

    private Button sendButton;
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new SendGPS(getApplicationContext()).sendGPS();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        int permission1 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int permission2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission1 == PackageManager.PERMISSION_DENIED || permission2 == PackageManager.PERMISSION_DENIED) {

            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("권한이 필요합니다.")
                        .setMessage("이 기능을 사용하기 위해서는 단말기의 \"전화걸기\" 권한이 필요합니다. 계속하시겠습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
                                }

                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "기능을 취소했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create()
                        .show();
            }

            else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
            }

        }
                    /* CALL_PHONE의 권한이 있을 때 */
        else {

        }




        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiListView = (ListView) findViewById(R.id.wifi_list_view);
        scan();

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
            }
            else {
                Toast.makeText(MainActivity.this, "권한 요청을 거부했습니다.", Toast.LENGTH_SHORT).show();
            }

        }
    }


    public void scan(){
        wifiManager.startScan();
        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        getApplicationContext().registerReceiver(wifiReceiver, intentFilter);
        Log.i("hari ", "scan");
    }

    private BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)){
                searchWifi();
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.M)
    public void searchWifi(){
        Log.i("hari ", "searchWifi");
        wifiListView.setAdapter(wifiListAdapter);
        apList = wifiManager.getScanResults();
        if(wifiManager.getScanResults() != null){
            Log.i("hari ", "searchWifi not null");
            Log.i("hari ", "searchWificount"+String.valueOf(apList.size()));
            int size = apList.size();
            wifiListAdapter.clear();
            for(int i = 0; i < size; i++) {
                WiFiData wifiData = new WiFiData();
                wifiData.BSSID = apList.get(i).BSSID;
                wifiData.SSID = apList.get(i).SSID;
                wifiData.level = apList.get(i).level;
                wifiData.venueName = String.valueOf(apList.get(i).venueName);
                wifiListAdapter.add(wifiData);

                //scanResult = (ScanResult) apList.get(i);
                //wifiListAdapter.add
                //wifiListAdapter.add(scanResult);
                //Log.i("hari ", String.valueOf(scanResult));
                //wifiListAdapter.notifyDataSetChanged();
            }
            wifiListAdapter.sort();
            wifiListAdapter.notifyDataSetChanged();
        }
        else
            Log.i("hari ", "searchWifi null");

        scan();
    }
}
