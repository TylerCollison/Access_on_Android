package com.access.accessonandroid.FingerScan;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import static android.content.Context.KEYGUARD_SERVICE;

/**
 * Created by Daniel Bond on 10/24/2017.
 * Adapted code from an online tutorial
 *      http://www.techotopia.com/index.php/An_Android_Fingerprint_Authentication_Tutorial
 *
 *  This class is needed to scan fingerprints.
 *  A digital representation of a fingerprint is stored in the crypoObject.
 *  The FingerScanHandler helper uses this object and the FingerprintManager from the operating
 *  system to determine the correct outcome.
 */

public class FingerScanner {
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private Cipher cipher;
    private FingerprintManager.CryptoObject cryptoObject;
    private Context context;
    private String KEY_NAME = "example";
    private boolean match = false;

    public FingerScanner(Context c) {
        context = c;
    }

    public void scanFinger() {
        keyguardManager = (KeyguardManager) context.getSystemService(KEYGUARD_SERVICE);
        fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);

        if (!keyguardManager.isKeyguardSecure()) {
            Toast.makeText(context, "Lock screen must be secure", Toast.LENGTH_LONG).show();
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "App must have permission to use finger scanner", Toast.LENGTH_LONG).show();
        }
        if (!fingerprintManager.hasEnrolledFingerprints()) {
            Toast.makeText(context, "There must be at least one enrolled fingerprint", Toast.LENGTH_LONG).show();
        }
        generateKey();
        if(cipherInit()){
            cryptoObject = new FingerprintManager.CryptoObject(cipher);
            FingerScanHandler helper  = new FingerScanHandler(context, this );
            helper.startAuth(fingerprintManager, cryptoObject);
        }
    }

    /**
     * Once the Fingerprint is matched then this can be called to set the matched value to true
     */
    public void matched(){
        match = true;
    }

    /**
     * This method is called to check if this fingerscanner object has found a matched fingerprint
     */
    public boolean getMatch(){
        return match;
    }

    private void generateKey() {
        try {
            keyStore = (KeyStore.getInstance("AndroidKeyStore"));
            keyGenerator = (KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore"));
            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).setBlockModes(KeyProperties.BLOCK_MODE_CBC).setUserAuthenticationRequired(true).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).build());
            keyGenerator.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean cipherInit(){
        try{
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"+ KeyProperties.BLOCK_MODE_CBC+"/"+KeyProperties.ENCRYPTION_PADDING_PKCS7);
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        }   catch ( Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
