package com.example.mobitechtask.main.fragments.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobitechtask.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import static android.content.Context.SENSOR_SERVICE;


public class lightFragment extends Fragment  implements SensorEventListener {
    TextView textView;
    SensorManager sensorManager;
    //    SensorEventListener listener;
    Sensor brightness;
    CircularProgressBar progressBar;
Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_light, container, false);
        context = getActivity();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        textView = root.findViewById(R.id.tv_text);
        progressBar = root.findViewById(R.id.circularProgressBar);

        setupSensorStuff();

        return root ;
    }

    private String brightness(float brightness) {
        int br = (int) brightness;
        if (br == 0) return "Black";
        else if (br > 0 && br <= 10) return "Dark";
        else if (br > 10 && br <= 50) return "Grey";
        else if (br > 50 && br <= 5000) return "Normal";
        else if (br > 5000 && br <= 25000) return "Incredibly bright";
        else return "this light will blind you";
    }


    private void setupSensorStuff() {
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        brightness = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, brightness, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float light = event.values[0];
            textView.setText("Light Sensor : " + light + "\n" + brightness(light));

            progressBar.setProgressWithAnimation(light);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this); // to avoid memory leaks

    }
}