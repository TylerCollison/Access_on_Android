package com.access.accessonandroid;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.access.accessonandroid.Data.EmployeeRecord;

import java.io.IOException;

/**
 * @author Tyler Collison
 *
 * This activity is responsible for initiating authentication via fingerprint and
 * managing the UI displayed during the fingerprinting phase of authentication.
 */
public class FingerScannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_scanner);

        //Start authentication
        new FingerprintAuthTask().execute(this);
    }

    private static class FingerprintAuthTask extends AsyncTask<Object, Object, Object> {
        @Override
        protected Object doInBackground(Object[] objects) {
            final Activity context = (Activity) objects[0];

            //Update the access id
            try {
                EmployeeRecord.getInstance().RefreshAccessIDFromServer(
                        context.getString(R.string.data_server_address) +
                                context.getString(R.string.id_route));
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.v("FingerprintAuthTask", "Started Task");

            //Authenticate the user
            Authenticator.getInstance().auth(context);

            //Fingerprint authentication is complete, close this activity
            context.finish();

            return null;
        }
    }
}
