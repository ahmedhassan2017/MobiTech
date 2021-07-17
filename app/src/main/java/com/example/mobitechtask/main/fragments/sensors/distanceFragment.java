package com.example.mobitechtask.main.fragments.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobitechtask.R;


public class distanceFragment extends Fragment {
    // proximity Sensor   (position sensor)
    TextView sensorStatusTV;
    SensorManager sensorManager;
    Sensor proximitySensor;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         context = getActivity();
        // Inflate the layout for this fragment
         View root = inflater.inflate(R.layout.fragment_distance, container, false);

        // sensor
        sensorStatusTV = root.findViewById(R.id.sensorStatusTV);
        // calling sensor service.
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        // from sensor service we are
        // calling proximity sensor
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        // handling the case if the proximity
        // sensor is not present in users device.
        if (proximitySensor == null) {
            Toast.makeText(getActivity(), "No proximity sensor found in device.", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        } else {
            // registering our sensor with sensor manager.
            sensorManager.registerListener(proximitySensorEventListener,
                    proximitySensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        return root;
    }
    SensorEventListener proximitySensorEventListener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // method to check accuracy changed in sensor.
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            // check if the sensor type is proximity sensor.
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                if (event.values[0] == 0) {
                    // here we are setting our status to our textview..
                    // if sensor event return 0 then object is closed
                    // to sensor else object is away from sensor.
                    sensorStatusTV.setText("That's enough");
                    sensorStatusTV.setTextColor(context.getResources().getColor(R.color.nbety));



                } else {

                    sensorStatusTV.setText("Get Closer to Proximity Sensor");
                    sensorStatusTV.setTextColor(context.getResources().getColor(R.color.DarkGrey));


                }
            }
        }
    };

}