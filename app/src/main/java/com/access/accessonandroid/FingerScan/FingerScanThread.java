package com.access.accessonandroid.FingerScan;

/**
 * Created by Daniel Bond on 10/26/2017.
 * This is a runnable which will run until the fingerscanner finds a match
 * There is a five second wait between scans to avoid issues with phone hardware
 * Works with FingerScanner and FingerScanHandler
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
        fingerScanner.scanFinger();
        while(!fingerScanner.getMatch()){
            fingerScanner.scanFinger();
            try{
                Thread.sleep(5000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
