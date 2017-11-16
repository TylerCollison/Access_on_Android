package com.access.accessonandroid;

import android.content.Context;

import com.access.accessonandroid.FacialRecog.FacialRecogRunnable;
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
        //finger scanner runnable repeatedly runs until there is a match
        Runnable fingerRunner = new FingerScanThread(fingerScanner);
        Thread fingerthread = new Thread(fingerRunner);
        fingerthread.start();

        //create runnable for facial scan I would like to be this easy
        //Runnable faceRunner = new FacialRecogRunnable();
        //Thread facethread = new Thread(faceRunner);
        // facethread.start();

        try {
            fingerthread.join();
            //facethread.join()
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}