package com.nunuplanet.test.communication;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nunuplanet.test.MainActivity;
import com.nunuplanet.test.database.GPSTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hari on 2016-10-09.
 */
public class SendGPS {

    private Context context;

    public SendGPS(Context context){
        this.context = context;
    }

    public void sendGPS() throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONArray requestBody = GPSTools.getGPS();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, MainActivity.URL, requestBody,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

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
