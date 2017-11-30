package com.access.accessonandroid;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.access.accessonandroid.Data.EmployeeRecord;
import com.access.accessonandroid.UserRegistration.Registration;
import com.access.accessonandroid.UserRegistration.passwordChange;

/**
 * @author Tyler Collison
 * @author Megan Goins
 * @author Mae Hutchison
 *
 * This class is responsible for managing the main activity screen, which appears at application
 * launch and acts as the central activity that unifies the application.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context mainContext = this;

        //Determine whether the employee record has credentials
        if (!EmployeeRecord.getInstance().HasCredentials()) {
            //Have user enter credentials
            startActivity(new Intent(mainContext, Registration.class));
        }

        //Start fingerprint scanning authentication activity on Authentication button click
        Button authButton = findViewById(R.id.authButton);
        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mainContext, FingerScannerActivity.class));
            }
        });

        //Start the change password activity on Change Password button click
        Button chngPasswordButton = findViewById(R.id.passwordButton);
        chngPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mainContext, passwordChange.class));
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        //Invalidate the user's credentials on hibernation
        Authenticator.getInstance().invalidateAuthentication();
    }
}