package com.access.accessonandroid.NFC.Services;

import android.content.Context;
import android.nfc.cardemulation.HostApduService;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.access.accessonandroid.Authenticator;
import com.access.accessonandroid.Data.EmployeeRecord;
import com.access.accessonandroid.Emulator;
import com.access.accessonandroid.FingerScan.FingerScanThread;
import com.access.accessonandroid.FingerScan.FingerScanner;
import com.access.accessonandroid.NFC.HCE.HCEAccessProtocolEngine;
import com.access.accessonandroid.NFC.HCE.HCECommand;
import com.access.accessonandroid.NFC.HCE.IHCEProtocolEngine;
import com.access.accessonandroid.Network.INetworkCallback;
import com.access.accessonandroid.Network.NetworkOperation.INetworkOperation;
import com.access.accessonandroid.R;

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
    private ProtectedADPUMessage message = new ProtectedADPUMessage();

    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        message.cancel(true);
        message = new ProtectedADPUMessage();
        message.execute(this, protocolEngine.getResponse(commandApdu), getApplicationContext());
        return null;
    }

    @Override
    public void onDeactivated(int reason) {}

    private static class ProtectedADPUMessage extends AsyncTask<Object, Object, Object> {
        @Override
        protected Object doInBackground(Object[] objects) {
            final HostApduService transmitter = (HostApduService) objects[0];
            final byte[] command = (byte[])objects[1];
            final Context context = (Context)objects[2];

            //Update the access id
            try {
                EmployeeRecord.getInstance().RefreshAccessIDFromServer(
                        context.getString(R.string.data_server_address) +
                                context.getString(R.string.id_route));
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Finger scanning component
           /* FingerScanner fingerScanner = new FingerScanner(context);

            //finger scanner runnable repeatedly runs until there is a match
            Runnable fingerRunner = new FingerScanThread(fingerScanner);
            new Thread(fingerRunner).start();

            //Spin lock
            while(fingerScanner.getMatch()) {
                Log.v("NFC", "Waiting for fingerprint match");
            }*/
            Runnable emulator = new Runnable() {
                @Override
                public void run() {
                    Authenticator.auth(context);

                    //Send response
                    Log.v("NFC", "Transmitting NFC message");
                    transmitter.sendResponseApdu(command);
                }
            };
            emulator.run();
            return null;
        }
    }
}
