package com.access.accessonandroid.UserRegistration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.access.accessonandroid.R;
import com.access.accessonandroid.Data.EmployeeRecord;

/**
 * @author Megan Goins
 *
 * This activity is responsible for allowing the user to change his or her password.
 */
public class passwordChange extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.access.accessonandroid.R.layout.activity_password_change);

        //Store the Change Password button
        Button changePasswordButton = (Button) findViewById(R.id.changePasswordButton);

        //Change the user's password on Change Password button click
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Store the password input fields
                EditText passwordTextBox = (EditText) findViewById(R.id.newPassword);
                EditText confirmTextBox = (EditText) findViewById(R.id.confirmNewpassword);

                //Get the user-entered password information
                String enteredPassword = passwordTextBox.getText().toString();
                String confirmedPassword = confirmTextBox.getText().toString();

                //Get the employee record for the user
                EmployeeRecord userRecord = EmployeeRecord.getInstance();

                //Determine whether the supplied password matches the employee record
                if (enteredPassword.equals(confirmedPassword)) {
                    //Update the user's password
                    userRecord.updateUserPassword(confirmedPassword);
                    //Display a success message
                    Toast.makeText(getApplicationContext(), "Password changed",Toast.LENGTH_SHORT).show();
                    //Close this activity
                    finish();
                }
                else {
                    //Display an error
                    Toast.makeText(getApplicationContext(), "Passwords do not match.",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}