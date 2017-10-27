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



        //Testing Code by Mike
        FacialRegSu tst_su = new FacialRegSu(getApplicationContext());
        Image pict_1 = FacialRegSu.makeImageFromS3File("source.jpg", "infosecurity");
        Image pict_2 = FacialRegSu.makeImageFromS3File("my_face.jpg", "infosecurity");
        boolean result_tmp = tst_su.compareFacesThreadedBlocking(pict_1, pict_2);
        System.out.println(result_tmp + " Lalalala");
        System.out.println("The result is: ");
//        System.out.println(tst_su.getThreadResult() + ": 1");
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        }catch(Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(tst_su.getThreadResult() + ": 2");
//        System.out.println(tst_su.getThreadResult() + ": 3");
//        System.out.println(tst_su.getThreadResult() + ": 4");
        System.out.println("What?");

    }
}