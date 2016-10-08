package com.nunuplanet.test;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

/**
 * Created by hari on 10/6/2016.
 */
public class WiFiManager {
    private ScanResult scanResult;
    private WifiManager wifiManager;
    private List<ScanResult> apList;
    private List<WiFiData> wifiList;
    private Context context;
    private IntentFilter intentFilter;
    private ListView listView;
    private WifiListAdapter wifiListAdapter = new WifiListAdapter();

    public WiFiManager(Context context, ListView listView){
        this.context = context;
        this.listView = listView;
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        Log.i("hari ", "wifi");
    }

    public void scan(){
        wifiManager.startScan();
        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        context.registerReceiver(wifiReceiver, intentFilter);
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
        listView.setAdapter(wifiListAdapter);
        apList = wifiManager.getScanResults();
        //Collections.sort(apList. new RSSICompare());
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
                //wifiListAdapter.add(scanResult);
                //Log.i("hari ", String.valueOf(scanResult));

            }
            wifiListAdapter.sort();
            wifiListAdapter.notifyDataSetChanged();
        }
        else
            Log.i("hari ", "searchWifi null");
    }
}
