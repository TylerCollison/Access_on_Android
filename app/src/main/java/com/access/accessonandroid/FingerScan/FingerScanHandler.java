package com.access.accessonandroid.FingerScan;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.access.accessonandroid.Authenticator;

/**
 * Created by danie_000 on 10/24/2017.
 */

public class FingerScanHandler extends FingerprintManager.AuthenticationCallback {
    private Context context;
    private CancellationSignal cancellationSignal;
    private FingerScanner scanner;

    public FingerScanHandler (Context c, FingerScanner fingerScanner){
        context = c;
        scanner = fingerScanner;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject){

        cancellationSignal = new CancellationSignal();

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "App must have permission to use finger scan", Toast.LENGTH_LONG);
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString){
        Toast.makeText(context, "Auth Error",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence errString){
        Toast.makeText(context, "Auth Help",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onAuthenticationFailed(){
        Toast.makeText(context, "Finger Auth Failed",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result){
        Toast.makeText(context, "Finger Auth Success!",Toast.LENGTH_LONG).show();
        scanner.matched();
        Authenticator.fingerAuth = true;
        Authenticator.totalAuth =Authenticator.fingerAuth;//until face scan is working
    }
}
