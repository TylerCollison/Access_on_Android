package com.access.accessonandroid;

import android.content.Context;
import android.nfc.cardemulation.HostApduService;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.access.accessonandroid.Camera.FrontFacingCameraScanner;
import com.access.accessonandroid.Camera.ICamera;
import com.access.accessonandroid.Camera.ImageCaptureCallback;
import com.access.accessonandroid.Data.EmployeeRecord;
import com.access.accessonandroid.FingerScan.FingerScanThread;
import com.access.accessonandroid.FingerScan.FingerScanner;
import com.access.accessonandroid.NFC.Services.AccessCardService;

import java.io.IOException;
import java.nio.ByteBuffer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        startActivity(new Intent(MainActivity.this, CameraActivity.class));
        //maybe this will all be abstracted away from here later
//        setContentView(R.layout.activity_main);
//        startActivity(new Intent(MainActivity.this, Registration.class));

        //code to run authenticator class which will authenticate the user
//        Authenticator authenticator = new Authenticator();
//        authenticator.auth(this);
    }
}