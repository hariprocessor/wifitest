package com.nunuplanet.test.communication;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nunuplanet.test.MainActivity;
import com.nunuplanet.test.database.GPSTools;
import com.nunuplanet.test.database.StepsTools;
import com.nunuplanet.test.database.WiFiTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hari on 2016-10-16.
 */
public class SendData {
    private Context context;

    public SendData(Context context){
        this.context = context;
    }

    public void sendGPS() throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONArray gpsJson = GPSTools.getGPS();
        JSONObject requestBody = new JSONObject();
        requestBody.put("gps", gpsJson);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, MainActivity.URL, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }



        };



        queue.add(request);
    }

    public void sendWifi() throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONArray wifiJson = WiFiTools.getWiFi();

        JSONObject requestBody = new JSONObject();
        requestBody.put("wifi", wifiJson);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, MainActivity.URL, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }



        };



        queue.add(request);
    }
    public void sendStep() throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONArray stepJson = StepsTools.getSteps();

        JSONObject requestBody = new JSONObject();
        requestBody.put("step", stepJson);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, MainActivity.URL, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }



        };



        queue.add(request);
    }

}
