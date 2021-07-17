package com.example.mobitechtask.main.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobitechtask.R;

import java.util.Arrays;

public class ReportActivity extends AppCompatActivity {
    TextView textview;
    String[] reportData = new String[6];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textview = (TextView) findViewById(R.id.textViewBatteryHealth);


        reportData = getIntent().getStringArrayExtra("data");
        for (int i = 0 ; i<reportData.length;i++)
        {
            textview.append(reportData[i]+"\n\n");
        }




    }



}
