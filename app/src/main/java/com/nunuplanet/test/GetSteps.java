package com.nunuplanet.test;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

import com.nunuplanet.test.database.StepsTools;

/**
 * Created by hari on 2016-10-16.
 */
public class GetSteps {
    private SensorManager sensorManager;
    private Context context;
    private Sensor sensor;

    public GetSteps(Context context){
        this.context = context;
    }

    public void step() {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (sensor != null) {
            sensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    StepsTools.saveSteps(TimeStamp.getTimeStamp());
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            }, sensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(context, "Count sensor not available!", Toast.LENGTH_SHORT).show();
        }
    }

}
