package com.access.accessonandroid;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.access.accessonandroid.Camera.CameraActivity;
import com.access.accessonandroid.FacialRecog.FacialRecogRunnable;
import com.access.accessonandroid.FingerScan.FingerScanThread;
import com.access.accessonandroid.FingerScan.FingerScanner;

/**
 * Fingerprint scanning and facial recognition
 */

public class Authenticator {
    public static boolean faceAuth = false, fingerAuth=false, totalAuth = false;

    public static void auth(Context context){
        Log.v("test","Auth Started");
        //Finger scanning component
        FingerScanner fingerScanner = new FingerScanner(context);
        //finger scanner runnable repeatedly runs until there is a match
        Runnable fingerRunner = new FingerScanThread(fingerScanner);
        Thread fingerthread = new Thread(fingerRunner);
        fingerthread.start();

        Log.v("test","Auth Success");
    }

    public static void reset(){
        faceAuth = false; fingerAuth=false; totalAuth = false;
    }
}