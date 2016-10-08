package com.nunuplanet.test;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by hari on 10/6/2016.
 */
public class WifiListAdapter extends BaseAdapter{
    //private ArrayList<ScanResult> arrayList;
    private ArrayList<WiFiData> arrayList = new ArrayList<>();

    public WifiListAdapter(){
        //arrayList = new ArrayList<ScanResult>();
        //arrayList = new ArrayList<WiFiData>();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();
        WiFiViewHolder holder;
        if(view == null){
            holder = new WiFiViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.wifi_list_item, viewGroup, false);

            holder.bssidTextView = (TextView) view.findViewById(R.id.bssid_text_view);
            holder.ssidTextView = (TextView) view.findViewById(R.id.ssid_text_view);
            holder.levelTextView = (TextView) view.findViewById(R.id.level_text_view);
            holder.venueNameTextView = (TextView) view.findViewById(R.id.venue_name_text_view);

            view.setTag(holder);
            /*
            bssidTextView.setText(arrayList.get(i).BSSID);
            ssidTextView.setText(arrayList.get(i).SSID);
            levelTextView.setText(String.valueOf(arrayList.get(i).level));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                venueNameTextView.setText(String.valueOf(arrayList.get(i).venueName));
            }
            */
        }
        else{
            holder = (WiFiViewHolder) view.getTag();
        }

        WiFiData wiFiData = arrayList.get(i);

        holder.bssidTextView.setText(arrayList.get(i).BSSID);
        holder.ssidTextView.setText(arrayList.get(i).SSID);
        holder.levelTextView.setText(String.valueOf(arrayList.get(i).level));
        holder.venueNameTextView.setText(String.valueOf(arrayList.get(i).venueName));
        return view;
    }



/*
    public void add(ScanResult scanResult){
        arrayList.add(scanResult);
    }
*/

    public void add(String BSSID, String SSID, int level, String venueName){
        WiFiData wiFiData = new WiFiData();
        wiFiData.BSSID = BSSID;
        wiFiData.SSID = SSID;
        wiFiData.level = level;
        wiFiData.venueName = venueName;
        arrayList.add(wiFiData);
    }

    public void add(WiFiData wiFiData){
        arrayList.add(wiFiData);
    }
    public void remove(int _position){
        arrayList.remove(_position);
    }

    public void clear(){
        Log.i("hari ", "array list size "+String.valueOf(arrayList.size()));
        arrayList.clear();
        Log.i("hari ", "array list size "+String.valueOf(arrayList.size()));
    }

    public void sort(){
        Collections.sort(arrayList, WiFiData.COMPARATOR);
        notifyDataSetChanged();
    }
}
