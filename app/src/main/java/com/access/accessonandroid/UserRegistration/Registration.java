package com.access.accessonandroid.UserRegistration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.access.accessonandroid.Data.EmployeeRecord;
import com.access.accessonandroid.R;
import com.access.accessonandroid.UserRegistration.passwordChange;

import android.content.Intent;

/**
 * @author Megan Goins
 *
 * This class is responsible for managing the initial sign-in screen, at which the user enters
 * his or her username and password for later authentication with the server.
 */
public class Registration extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Store the Sign-in button
        Button signInButton = (Button) findViewById(R.id.signInButton);

        //Sign the user in on Sign-in button click
        signInButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Get the user-entered username and password
                EditText username = (EditText)findViewById(R.id.username);
                EditText password = (EditText)findViewById(R.id.password);
                String enteredUsername = username.getText().toString();
                String enteredPassword = password.getText().toString();

                //Get the employee record for the user
                EmployeeRecord userRecord = EmployeeRecord.getInstance();

                //Determine whether the supplied username matches the record
                if(userRecord.isUser(enteredUsername)){
                    //Determine whether the supplied password matches the record
                    String storedPassword = userRecord.getUserPassword();
                    if(!storedPassword.equals(enteredPassword))
                        //Display an error message
                        Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
                    else {
                        //Set the user's credentials in the employee record
                        userRecord.SetCredentials(enteredUsername, enteredPassword);
                        //Close this activity
                        finish();
                    }
                }
                else {
                    //Display an error message
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
