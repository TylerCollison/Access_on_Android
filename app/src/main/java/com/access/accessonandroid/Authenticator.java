package com.access.accessonandroid;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.access.accessonandroid.Camera.CameraActivity;
import com.access.accessonandroid.FingerScan.FingerScanThread;
import com.access.accessonandroid.FingerScan.FingerScanner;

/**
 * @author Daniel Bond
 * @author Tyler Collison
 *
 * This is the class that uses both the Facial Recognition and Fingerprint scanning components
 * to authenticate the user and keep track of authentication state.
 *
 * A singleton pattern is used so that we can access state throughout the application.
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
    private boolean requiresFacialRecognition = true;

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

        //finger scanner runnable runs until there is a match
        Runnable fingerRunner = new FingerScanThread(fingerScanner);
        Thread fingerthread = new Thread(fingerRunner);
        fingerthread.start();

        try {
            fingerthread.join();
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