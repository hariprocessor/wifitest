package com.nunuplanet.test;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.widget.ListView;

import com.nunuplanet.test.database.WiFiTools;
import com.nunuplanet.test.wifi.WiFiData;
import com.nunuplanet.test.wifi.WifiListAdapter;

import java.util.List;

/**
 * Created by hari on 10/10/2016.
 */
public class GetWiFi {
    private Context context;
    private WifiManager wifiManager;
    private ListView wifiListView;
    private WifiListAdapter wifiListAdapter = new WifiListAdapter();
    private IntentFilter intentFilter;
    private List<ScanResult> apList;

    public GetWiFi(Context context, ListView wifiListView){
        this.context = context;
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        this.wifiListView = wifiListView;
    }
    public void scan() {
        wifiManager.startScan();
        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        context.registerReceiver(wifiReceiver, intentFilter);
        //Log.i("hari ", "scan");
    }
    @TargetApi(Build.VERSION_CODES.M)
    public void searchWifi() {
        wifiListView.setAdapter(wifiListAdapter);
        apList = wifiManager.getScanResults();
        if (wifiManager.getScanResults() != null) {
            int size = apList.size();
            wifiListAdapter.clear();
            for (int i = 0; i < size; i++) {
                WiFiData wifiData = new WiFiData();
                wifiData.BSSID = apList.get(i).BSSID;
                wifiData.SSID = apList.get(i).SSID;
                wifiData.level = apList.get(i).level;
                wifiData.venueName = String.valueOf(apList.get(i).venueName);
                wifiListAdapter.add(wifiData);

                WiFiTools.saveWiFi(wifiData.BSSID, wifiData.SSID, wifiData.level, TimeStamp.getTimeStamp());
            }
            wifiListAdapter.sort();
            wifiListAdapter.notifyDataSetChanged();
        }
        scan();
    }

    private BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                searchWifi();
            }
        }
    };


}
