package com.example.mobitechtask.main.fragments.sensors;

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

import java.util.List;

import static android.content.Context.SENSOR_SERVICE;


public class shakeFragment extends Fragment {
    SensorManager sm = null;
    TextView textView1 = null;
    List list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_shake, container, false);

        /* Get a SensorManager instance */
        sm = (SensorManager)getActivity().getSystemService(SENSOR_SERVICE);

        textView1 = (TextView)root.findViewById(R.id.textView1);

        list = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if(list.size()>0){
            sm.registerListener(sel, (Sensor) list.get(0), SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            Toast.makeText(getActivity(), "Error: No Accelerometer.", Toast.LENGTH_LONG).show();
        }

        return root;
    }

    // ACCELEROMETER Sensor (Motion sensor)
    SensorEventListener sel = new SensorEventListener(){
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            textView1.setText("x: "+values[0]+"\ny: "+values[1]+"\nz: "+values[2]);
        }
    };


    @Override
    public void onStop() {
        if(list.size()>0){
            sm.unregisterListener(sel);
        }
        super.onStop();
    }
}