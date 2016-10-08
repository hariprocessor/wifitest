package com.nunuplanet.test.database;

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

    public static void saveGPS(final double latitude, final double longitude){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {
                GPS gps = realm.createObject(GPS.class);
                gps.setLatitude(latitude);
                gps.setLongitude(longitude);
                gps.setTimeStamp(TimeStamp.getTimeStamp());
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
                .lessThan("TimeStamp", TimeStamp.getTodayTimeStamp())
                .greaterThan("TimeStamp", TimeStamp.getYesterdayTimeStamp())
                .findAll();

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
            jsonObject.put("time_stamp",query.get(i).getTimeStamp());
            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }


}
