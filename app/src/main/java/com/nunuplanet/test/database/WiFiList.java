package com.nunuplanet.test.database;

import java.util.ArrayList;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hari on 2016-10-09.
 */
public class WiFiList extends RealmObject{
    private String BSSID;
    private String SSID;
    private int level;

    @PrimaryKey
    private long timeStamp;

    public void setBSSID(String BSSID){
        this.BSSID = BSSID;
    }

    public String getBSSID(){
        return BSSID;
    }

    public void setSSID(String SSID){
        this.SSID = SSID;
    }

    public String getSSID(){
        return SSID;
    }

    public void setLevel(int level){
        this.level = level;
    }

    public int getLevel(){
        return level;
    }

    public void setTimeStamp(long timeStamp){
        this.timeStamp = timeStamp;
    }

    public long getTimeStamp(){
        return timeStamp;
    }
}
