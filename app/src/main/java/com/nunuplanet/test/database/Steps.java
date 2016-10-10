package com.nunuplanet.test.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hari on 2016-10-10.
 */
public class Steps extends RealmObject {
    private int step;

    @PrimaryKey
    private long timeStamp;

    public void setStep(int step){
        this.step = step;
    }

    public int getStep(){
        return step;
    }

    public void setTimeStamp(long timeStamp){
        this.timeStamp = timeStamp;
    }

    public long getTimeStamp(){
        return timeStamp;
    }
}
