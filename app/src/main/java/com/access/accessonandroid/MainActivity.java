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

import com.access.accessonandroid.Camera.CameraActivity;
import com.access.accessonandroid.Camera.FrontFacingCameraScanner;
import com.access.accessonandroid.Camera.ICamera;
import com.access.accessonandroid.Camera.ImageCaptureCallback;
import com.access.accessonandroid.Data.EmployeeRecord;
import com.access.accessonandroid.FingerScan.FingerScanThread;
import com.access.accessonandroid.FingerScan.FingerScanner;
import com.access.accessonandroid.NFC.Services.AccessCardService;
import com.access.accessonandroid.UserRegistration.Registration;
import com.access.accessonandroid.UserRegistration.passwordChange;

import java.io.IOException;
import java.nio.ByteBuffer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context mainContext = this;

        //Determine whether the employee record has credentials
        if (!EmployeeRecord.getInstance().HasCredentials()) {
            //Have user enter credentials
            startActivity(new Intent(mainContext, Registration.class));
        }

        Button authButton = findViewById(R.id.authButton);
        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mainContext, FingerScannerActivity.class));
            }
        });

        Button chngPasswordButton = findViewById(R.id.passwordButton);
        chngPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mainContext, passwordChange.class));
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        Authenticator.getInstance().invalidateAuthentication();
    }
}