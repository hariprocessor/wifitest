package com.nunuplanet.test.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hari on 2016-10-08.
 */
public class GPS extends RealmObject{
    private double latitude;
    private double longitude;

    @PrimaryKey
    private long timeStamp;

    public double getLatitude(){
        return latitude;
    }
    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public double getLongitude(){
        return longitude;
    }
    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public long getTimeStamp(){
        return timeStamp;
    }
    public void setTimeStamp(long timeStamp){
        this.timeStamp = timeStamp;
    }

}
