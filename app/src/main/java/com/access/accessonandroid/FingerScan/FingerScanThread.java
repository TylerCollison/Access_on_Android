package com.access.accessonandroid.FingerScan;

import android.os.Looper;

/**
 * Created by danie_000 on 10/26/2017.
 * Thread for running the finger scanner until success
 */

public class FingerScanThread implements Runnable {
    private FingerScanner fingerScanner;
    //constructor added to use a fingerScanner passed in
    public FingerScanThread(FingerScanner fs){
        fingerScanner = fs;
    }

    //there is a while loop that scans until the finger is matched
    @Override
    public void run() {
        Looper.prepare();

        fingerScanner.scanFinger();
        while(!fingerScanner.getMatch()){
            fingerScanner.scanFinger();
            try{
                Thread.sleep(5000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        Looper.loop();
    }
}
