package com.nunuplanet.test.wifi;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.nunuplanet.test.TimeStamp;
import com.nunuplanet.test.database.WiFiTools;

import java.util.List;

/**
 * Created by hari on 2016-10-16.
 */
public class WiFiManager {
    private WifiManager wifiManager;
    private IntentFilter intentFilter;
    private List<ScanResult> apList;
    private Context context;

    public WiFiManager(Context context){
        this.context = context;
    }

    public void scan() {
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.startScan();
        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        context.getApplicationContext().registerReceiver(wifiReceiver, intentFilter);
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
        apList = wifiManager.getScanResults();
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

}
