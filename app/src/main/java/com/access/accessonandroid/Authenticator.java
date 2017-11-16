package com.access.accessonandroid;

import android.content.Context;

import com.access.accessonandroid.FingerScan.FingerScanThread;
import com.access.accessonandroid.FingerScan.FingerScanner;

/**
 * Implements Fingerprint scanning and facial recognition
 */

public class Authenticator {
    //
    public static void auth(Context context){
        //Finger scanning component
        FingerScanner fingerScanner = new FingerScanner(context);
        fingerScanner.scanFinger();
        //finger scanner runnable repeatedly runs until there is a match
        Runnable fingerRunner = new FingerScanThread(fingerScanner);
        new Thread(fingerRunner).start();
    }
}