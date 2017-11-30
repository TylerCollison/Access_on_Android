package com.access.accessonandroid.NFC.Services;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;

import com.access.accessonandroid.Authenticator;
import com.access.accessonandroid.Data.EmployeeRecord;
import com.access.accessonandroid.NFC.HCE.HCEAccessProtocolEngine;
import com.access.accessonandroid.NFC.HCE.HCECommand;
import com.access.accessonandroid.NFC.HCE.IHCEProtocolEngine;

/**
 * @author Tyler Collison
 *
 * The AccessCardService class extends the HostAdpuService class and provides the communication
 * interface between the NFC controller and the application. As such, this class is responsible for
 * receiving and responding to incoming NFC data.
 */
public class AccessCardService extends HostApduService  {

    //Store a protocol engine for HCE Access Control
    private static IHCEProtocolEngine protocolEngine =
            new HCEAccessProtocolEngine(EmployeeRecord.getInstance());

    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        //Determine whether the user has been authenticated
        if (Authenticator.getInstance().getAuthenticated()) {
            //Invalidate the user's authentication
            Authenticator.getInstance().invalidateAuthentication();
            //Send the appropriate response according to the protocol
            return protocolEngine.getResponse(commandApdu);
        } else {
            //Send the WAIT command
            return protocolEngine.getCommandCode(HCECommand.Wait);
        }
    }

    @Override
    public void onDeactivated(int reason) {
        //Invalidate the user's authentication, if present
        Authenticator.getInstance().invalidateAuthentication();
    }
}
