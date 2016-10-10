package com.nunuplanet.test.database;

import android.util.Log;

import com.nunuplanet.test.TimeStamp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by hari on 2016-10-10.
 */
public class StepsTools {
    public static void saveSteps(final long timestamp){
        Realm realm = Realm.getDefaultInstance();
        final long _timestamp = timestamp - timestamp%5;
        final RealmResults<Steps> query = realm.where(Steps.class).equalTo("timeStamp", _timestamp).findAll();
        //Log.i("hari", String.valueOf(_timestamp) + ", " + String.valueOf(query.size()));

        /*
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Steps steps = new Steps();
                steps.setTimeStamp(_timestamp);
                steps.setStep(1);
                realm.copyToRealmOrUpdate(steps);
            }
        });
*/

        if(query.size() != 0){
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    query.get(0).setStep(query.get(0).getStep()+1);
                    realm.copyToRealmOrUpdate(query.get(0));
                }
            });
        }
        else{
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    //Steps steps = realm.createObject(Steps.class);
                    Steps steps = new Steps();
                    steps.setTimeStamp(_timestamp);
                    steps.setStep(1);
                    realm.copyToRealmOrUpdate(steps);
                }
            });
        }

    }

    /*
    * execute after midnight
    * save the information of yesterday
    * */
    public static JSONArray getSteps() throws JSONException {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Steps> query = realm.where(Steps.class)
                .lessThan("timeStamp", TimeStamp.getTodayTimeStamp())
                .greaterThan("timeStamp", TimeStamp.getYesterdayTimeStamp())
                .findAll();

        /*
        for(int i = 0; i < query.size(); i++){
            query.get(i).getTimeStamp();
        }
        */

        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < query.size(); i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("steps", query.get(i).getStep());
            jsonObject.put("time_stamp",query.get(i).getTimeStamp());
            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }

}
