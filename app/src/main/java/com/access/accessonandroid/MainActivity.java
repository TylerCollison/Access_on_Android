package com.access.accessonandroid;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.media.Image;
import android.nfc.cardemulation.HostApduService;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;

import com.access.accessonandroid.Camera.FrontFacingCameraScanner;
import com.access.accessonandroid.Camera.ICamera;
import com.access.accessonandroid.Camera.ImageCaptureCallback;
import com.access.accessonandroid.FingerScan.FingerScanner;

import java.nio.ByteBuffer;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //maybe this will all be abstracted away from here later
        setContentView(R.layout.activity_main);
        //startActivity(new Intent(MainActivity.this, Registration.class));

        //code to run authenticator class which will authenticate the user
//        Authenticator authenticator = new Authenticator();
//        authenticator.auth(this);
    }
}