package com.example.mobitechtask.main.fragments.sensors;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.example.mobitechtask.R;
import com.google.zxing.Result;


public class scanFragment extends Fragment {
    private static final int CAMERA_REQUEST_CODE = 101;
    CodeScannerView scannerView;
    CodeScanner codeScanner;
    TextView textView;
    private static final String TAG = "scanFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_scan, container, false);

        scannerView = root.findViewById(R.id.scanner_view);
        textView = root.findViewById(R.id.txt);
        codeScanner = new CodeScanner(getActivity(), scannerView);
        setupPermissions();
        codeScanner();
        return root;
    }
    private void codeScanner() {
//         Parameters (default values)
        codeScanner.setCamera(CodeScanner.CAMERA_BACK); // or CAMERA_FRONT or specific camera id
        codeScanner.setFormats(CodeScanner.ALL_FORMATS); // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.setAutoFocusMode(AutoFocusMode.SAFE); // or CONTINUOUS
        codeScanner.setScanMode(ScanMode.CONTINUOUS);  // or CONTINUOUS or PREVIEW
        codeScanner.setAutoFocusEnabled(true); // Whether to enable auto focus or not
        codeScanner.setFlashEnabled(false);
        ;// Whether to enable flash or not

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(result.getText());
                    }
                });
            }
        });

        codeScanner.setErrorCallback(new ErrorCallback() {
            @Override
            public void onError(Exception error) {
                Log.d(TAG, "onError: " + error.getMessage());
            }
        });

        scannerView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeScanner.startPreview();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    public void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }


    private void setupPermissions() {
        int permission = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }

    }

    private void makeRequest() {
        ActivityCompat.requestPermissions(
                getActivity(), new String[]{Manifest.permission.CAMERA},
                CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if  (requestCode == CAMERA_REQUEST_CODE)
        {
            if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getActivity(), "you need camera permission", Toast.LENGTH_SHORT).show();
            }
            else { /* successful*/ }

        }

    }
}