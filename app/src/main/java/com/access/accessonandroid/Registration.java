package com.access.accessonandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.access.accessonandroid.Data.EmployeeRecord;

public class Registration extends AppCompatActivity {

    private Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        final EditText username = (EditText)findViewById(R.id.username);
        final EditText password = (EditText)findViewById(R.id.password);
        signInButton = (Button) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){//On click of signInButton
                String enteredUsername = username.getText().toString();
                String enteredPassword = password.getText().toString();

                //Set the credentials of the employee record
                EmployeeRecord.getInstance().SetCredentials(enteredUsername, enteredPassword);

                //TODO check if _username exists in database
                //if user not found
                //Toast.makeText(getApplicationContext(), "Invalid User",Toast.LENGTH_SHORT).show();
                String storedPassword = ""; //TODO get _password from database

                if(!storedPassword.equals(enteredPassword))
                    Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }
}
