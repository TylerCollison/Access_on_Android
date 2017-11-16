package com.access.accessonandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.access.accessonandroid.Camera.CameraActivity;
import com.access.accessonandroid.Data.EmployeeRecord;
import com.access.accessonandroid.FacialRecog.FacialRecogRunnable;
import com.access.accessonandroid.FingerScan.FingerScanThread;
import com.access.accessonandroid.FingerScan.FingerScanner;

/**
 * Implements Fingerprint scanning and facial recognition
 */

public class Authenticator {
    //Stores the singleton instance
    private static Authenticator instance = null;
    /**
     * Gets or creates the singleton instance
     * @return the singleton instance of this class
     */
    public static Authenticator getInstance() {
        if(instance == null) {
            instance = new Authenticator();
        }
        return instance;
    }

    private boolean isAuthenticated = false;

    private boolean requiresFingerprint = true;
    private boolean requiresFacialRecognition = false;

    public boolean getAuthenticated() {
        return isAuthenticated;
    }

    public void invalidateAuthentication() {
        isAuthenticated = false;
    }

    public void authenticate() {
        isAuthenticated = true;
    }

    public void auth(Context context){
        if (requiresFingerprint) {
            //Authenticate the user by his/her fingerprint
            authenticateFingerprint(context);
        } else if (requiresFacialRecognition) {
            //Authenticate the user by facial recognition
            context.startActivity(new Intent(context, CameraActivity.class));
        } else {
            //No authentication required
            authenticate();
        }
    }

    private void authenticateFace(Context context) {
        //Authenticate via facial recognition
        context.startActivity(new Intent(context, CameraActivity.class));
    }

    private void authenticateFingerprint(Context context) {
        //Finger scanning component
        FingerScanner fingerScanner = new FingerScanner(context);

        //finger scanner runnable repeatedly runs until there is a match
        Runnable fingerRunner = new FingerScanThread(fingerScanner);
        Thread fingerthread = new Thread(fingerRunner);
        fingerthread.start();

        try {
            fingerthread.join();
            //facethread.join()
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        Log.v("Authenticator", "Fingerprint Auth Success");

        if (requiresFacialRecognition) {
            //Authenticate via facial recognition
            authenticateFace(context);
        } else {
            authenticate();
        }
    }
}