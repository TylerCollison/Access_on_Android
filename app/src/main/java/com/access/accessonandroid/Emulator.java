package com.access.accessonandroid;

import android.content.Context;

/**
 * Created by danie_000 on 11/13/2017.
 */

public class Emulator implements Runnable {
    private Context contxt;
    public Emulator(Context context){
        contxt = context;
    }
    @Override
    public void run(){
        Authenticator.auth(contxt);
        //Authenticator will hang until authentication is complete
        //no true or false is needed

        //At this point we can send the NFC signal.

    }

}
