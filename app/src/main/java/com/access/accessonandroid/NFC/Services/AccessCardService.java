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

    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        if (Authenticator.getInstance().getAuthenticated()) {
            Authenticator.getInstance().invalidateAuthentication();
            return protocolEngine.getResponse(commandApdu);
        } else {
            return protocolEngine.getCommandCode(HCECommand.Wait);
        }
    }

    @Override
    public void onDeactivated(int reason) {
        Authenticator.getInstance().invalidateAuthentication();
    }
}
