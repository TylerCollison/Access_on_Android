package com.access.accessonandroid.NFC.Services;

import android.content.Context;
import android.nfc.cardemulation.HostApduService;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.access.accessonandroid.Data.EmployeeRecord;
import com.access.accessonandroid.FingerScan.FingerScanner;
import com.access.accessonandroid.NFC.HCE.HCEAccessProtocolEngine;
import com.access.accessonandroid.NFC.HCE.IHCEProtocolEngine;
import com.access.accessonandroid.Network.INetworkCallback;
import com.access.accessonandroid.Network.NetworkOperation.INetworkOperation;

import java.io.IOException;

/**
 * The AccessCardService class extends the HostAdpuService class and provides the communication
 * interface between the NFC controller and the application. As such, this class is responsible for
 * receiving and responding to incoming NFC data.
 *
 * Created by Tyler Collison on 10/24/2017.
 */
public class AccessCardService extends HostApduService  {

    private static IHCEProtocolEngine protocolEngine =
            new HCEAccessProtocolEngine(EmployeeRecord.getInstance());
    private static ProtectedADPUMessage message;

    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
//        if (message == null) {
//            message = new ProtectedADPUMessage();
//            message.execute(this, protocolEngine.getResponse(commandApdu), getApplicationContext());
//        }
        return protocolEngine.getResponse(commandApdu);
    }

    @Override
    public void onDeactivated(int reason) {}

    private static class ProtectedADPUMessage extends AsyncTask<Object, Object, Object> {
        @Override
        protected Object doInBackground(Object[] objects) {
            HostApduService transmitter = (HostApduService) objects[0];
            byte[] command = (byte[])objects[1];
            Context context = (Context)objects[2];

            //Finger scanning component
            FingerScanner fingerScanner = new FingerScanner(context);
            fingerScanner.scanFinger();

            //Spin lock
            while(!fingerScanner.getMatch()) {}

            transmitter.sendResponseApdu(command);

            message = null;

            return null;
        }
    }
}
