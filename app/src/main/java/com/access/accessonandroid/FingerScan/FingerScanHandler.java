package com.access.accessonandroid.FingerScan;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * Created by Daniel Bond on 10/24/2017.
 * Adapted code from an online tutorial
 *      http://www.techotopia.com/index.php/An_Android_Fingerprint_Authentication_Tutorial
 * This is the code that catches result of each call to FingerScanner.scanFinger()
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
            Toast.makeText(context, "App must have permission to use finger scan", Toast.LENGTH_LONG).show();
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
        Toast.makeText(context, "Auth Failed",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result){
        Toast.makeText(context, "Auth Success!",Toast.LENGTH_LONG).show();
        scanner.matched();// once matched the scanner flag is switched to true
    }
}
