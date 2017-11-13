package com.access.accessonandroid.Registration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.access.accessonandroid.R;
import com.access.accessonandroid.Data.EmployeeRecord;

public class passwordChange extends AppCompatActivity {

    private Button changePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(com.access.accessonandroid.R.layout.activity_password_change);
        changePasswordButton = (Button) findViewById(R.id.changePasswordButton);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {//On click of s
                EditText passwordTextBox = (EditText) findViewById(R.id.newPassword);
                EditText confirmTextBox = (EditText) findViewById(R.id.confirmNewpassword);
                String enteredPassword = passwordTextBox.getText().toString();
                String confirmedPassword = confirmTextBox.getText().toString();
                EmployeeRecord userRecord = Registration.getRecord();
                if (enteredPassword.equals(confirmedPassword)) {
                    userRecord.updateUserPassword(confirmedPassword);
                    Toast.makeText(getApplicationContext(), "Password changed",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Passwords do not match.",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}