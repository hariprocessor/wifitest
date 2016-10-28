package com.nunuplanet.test.database;

import android.util.Log;

import com.nunuplanet.test.TimeStamp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by hari on 2016-10-09.
 */
public class WiFiTools {
    public static void saveWiFi(final String BSSID, final String SSID, final int level, final long timestamp){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {
                //WiFiList wiFiList = realm.createObject(WiFiList.class, timestamp);
                WiFiList wiFiList = new WiFiList();
                wiFiList.setBSSID(BSSID);
                wiFiList.setSSID(SSID);
                wiFiList.setLevel(level);
                wiFiList.setTimeStamp(TimeStamp.getTimeStamp());
                realm.copyToRealmOrUpdate(wiFiList);
                //wiFiList.setTimeStamp(TimeStamp.getTimeStamp());
            }
        });
    }

    /*
    * execute after midnight
    * save the information of yesterday
    * */
    public static JSONArray getWiFi() throws JSONException {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<WiFiList> query = realm.where(WiFiList.class)
                .lessThan("timeStamp", TimeStamp.getTimeStamp())
                .greaterThan("timeStamp", TimeStamp.getOneHourAgoTimeStamp())
                .findAll();



        /*
        for(int i = 0; i < query.size(); i++){
            query.get(i).getTimeStamp();
        }
        */
        Log.i("hari getWiFi", String.valueOf(query.size()));

        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < query.size(); i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bssid", query.get(i).getBSSID());
            jsonObject.put("ssid", query.get(i).getSSID());
            jsonObject.put("level", query.get(i).getLevel());
            jsonObject.put("timestamp",query.get(i).getTimeStamp());
            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }

}
