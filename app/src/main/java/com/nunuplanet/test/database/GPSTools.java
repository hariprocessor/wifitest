package com.nunuplanet.test.database;

import android.util.Log;

import com.nunuplanet.test.TimeStamp;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by hari on 2016-10-08.
 */
public class GPSTools {

    public static void saveGPS(final double latitude, final double longitude, final long timestamp){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {
                //GPS gps = realm.createObject(GPS.class, timestamp);
                GPS gps = new GPS();
                gps.setTimeStamp(TimeStamp.getTimeStamp());
                gps.setLatitude(latitude);
                gps.setLongitude(longitude);
                realm.copyToRealmOrUpdate(gps);
                //gps.setTimeStamp(TimeStamp.getTimeStamp());
            }
        });
    }

    /*
    * execute after midnight
    * save the information of yesterday
    * */
    public static JSONArray getGPS() throws JSONException {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<GPS> query = realm.where(GPS.class)
                .lessThan("timeStamp", TimeStamp.getTimeStamp())
                .greaterThan("timeStamp", TimeStamp.getOneHourAgoTimeStamp())
                .findAll();

        Log.i("hari getGPS", String.valueOf(query.size()));

        /*
        for(int i = 0; i < query.size(); i++){
            query.get(i).getTimeStamp();
        }
        */

        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < query.size(); i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("latitude", query.get(i).getLatitude());
            jsonObject.put("longitude", query.get(i).getLongitude());
            jsonObject.put("timestamp",query.get(i).getTimeStamp());
            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }


}
