package com.example.mobitechtask.main.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobitechtask.main.activities.ReportActivity;
import com.example.mobitechtask.R;
import com.jaredrummler.android.device.DeviceName;


public class InfoFragment extends Fragment {

    String deviceID;
    // battery
    TextView mobId, batteryHealth, phoneMarketName, phoneName, androidVersion, sdkVersion;
    IntentFilter intentfilter;
    int deviceHealth;
    String currentBatteryHealth = "Battery Health ";
    Button report;
    String[] reportData = new String[6];
    private static final String FILE_NAME = "example.txt";

    private static final String TAG = "InfoFragment";
Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_info, container, false);
        context =getActivity();
        mobId = root.findViewById(R.id.mobile_id);
        batteryHealth = root.findViewById(R.id.battery_health);
        phoneMarketName = root.findViewById(R.id.phone_market_name);
        phoneName = root.findViewById(R.id.phone_name);
        androidVersion = root.findViewById(R.id.android_version);
        sdkVersion = root.findViewById(R.id.sdk_version);
        report = root.findViewById(R.id.report);


        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getReport();
            }
        });


        // Device ID
        deviceID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        reportData[0] = "Device ID : " + deviceID;
        mobId.setText("Device ID : " + deviceID);
        // battery
        intentfilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
// Here we are registering created broadcast receiver to the MainActivity and intent filter is listening for battery change events.
        context.registerReceiver(broadcastreceiver, intentfilter);


        // device name
        // setup Lib
        DeviceName.init(context);
        // Market name
        String brand = Build.BRAND;
        String deviceMarketName = brand + " " + DeviceName.getDeviceName();
        reportData[1] = ("Device brand : " + deviceMarketName);
        phoneMarketName.setText(deviceMarketName);
        // phone name
        reportData[2] = ("Product name : " + getDeviceName());
        phoneName.setText(getDeviceName());
        // android version
        reportData[3] = ( getAndroidVersion());
        androidVersion.setText(getAndroidVersion());

        // sdk
        int sdk = Build.VERSION.SDK_INT;
        reportData[4] = ("SDK Version : " + sdk);
        sdkVersion.setText("SDK Version : " + sdk);


        return root;

    }

    private void getReport() {
        Intent intent = new Intent(getActivity(), ReportActivity.class);
        intent.putExtra("data",reportData);
        startActivity(intent);

    }

    private boolean isExternalWritable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        } else return false;

    }


    // the Receiver that will receive the broadcast from system
    private BroadcastReceiver broadcastreceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            deviceHealth = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);

            if (deviceHealth == BatteryManager.BATTERY_HEALTH_COLD) {

                batteryHealth.setText(currentBatteryHealth + " = Cold");
                reportData[5] = (currentBatteryHealth + " = Cold");

            }

            if (deviceHealth == BatteryManager.BATTERY_HEALTH_DEAD) {

                batteryHealth.setText(currentBatteryHealth + " = Dead");
                reportData[5] = (currentBatteryHealth + " = Dead");
            }

            if (deviceHealth == BatteryManager.BATTERY_HEALTH_GOOD) {

                batteryHealth.setText(currentBatteryHealth + " = Good");
                reportData[5] = (currentBatteryHealth + " = Good");

            }

            if (deviceHealth == BatteryManager.BATTERY_HEALTH_OVERHEAT) {

                batteryHealth.setText(currentBatteryHealth + " = OverHeat");
                reportData[5] = (currentBatteryHealth + " = OverHeat");

            }

            if (deviceHealth == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {

                batteryHealth.setText(currentBatteryHealth + " = Over voltage");
                reportData[5] = (currentBatteryHealth + " = Over voltage");

            }

            if (deviceHealth == BatteryManager.BATTERY_HEALTH_UNKNOWN) {

                batteryHealth.setText(currentBatteryHealth + " = Unknown");
                reportData[5] = (currentBatteryHealth + " = Unknown");

            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE) {

                batteryHealth.setText(currentBatteryHealth + " = Unspecified Failure");
                reportData[5] = (currentBatteryHealth + " = Unspecified Failure");

            }
        }
    };


    /**
     * Returns the consumer friendly device name
     */
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }

    // get AndroidVersion
    public String getAndroidVersion() {
        String release = Build.VERSION.RELEASE;

        return "Android Version : " + release;
    }


}