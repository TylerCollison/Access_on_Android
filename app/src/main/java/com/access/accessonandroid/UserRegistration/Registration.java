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

public class Registration extends AppCompatActivity {

    private Button signInButton;
    private static EmployeeRecord userRecord;
    private static String correctUsername = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        signInButton = (Button) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){//On click of signInButton
                EditText username = (EditText)findViewById(R.id.username);
                EditText password = (EditText)findViewById(R.id.password);
                String enteredUsername = username.getText().toString();
                String enteredPassword = password.getText().toString();
//                userRecord = EmployeeRecord.getInstance();
//                if(userRecord.isUser(enteredUsername)){
//                    correctUsername = enteredUsername;
//                    String storedPassword = userRecord.getUserPassword();
//                    if(!storedPassword.equals(enteredPassword))
//                        Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
//                    else {
//                        startActivity(new Intent(Registration.this, passwordChange.class));
//                        userRecord.SetCredentials(correctUsername, enteredPassword);
//                        finish();
//                    }
//                }
//                else {
//                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
//                }

            }
        });
    }

    public static EmployeeRecord getRecord(){
        return userRecord;
    }
}
