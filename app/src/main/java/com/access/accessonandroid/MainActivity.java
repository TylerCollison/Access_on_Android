package com.access.accessonandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import com.access.accessonandroid.FacialRecog.FacialRegSu;
import com.access.accessonandroid.FacialRecog.FacialRecog;
import com.amazonaws.services.rekognition.model.Image;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //maybe this will all be abstracted away from here later
//        setContentView(R.layout.activity_main);
//        startActivity(new Intent(MainActivity.this, Registration.class));

        //code to run authenticator class which will authenticate the user
//        Authenticator authenticator = new Authenticator();
//        authenticator.auth(this);





    }
}