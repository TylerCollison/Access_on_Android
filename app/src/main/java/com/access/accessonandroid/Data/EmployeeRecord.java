package com.access.accessonandroid.Data;

import android.util.Log;

import com.access.accessonandroid.NFC.HCE.IHCEAccessCard;
import com.access.accessonandroid.Network.AbstractNetworkCallback;
import com.access.accessonandroid.Network.INetworkAdapter;
import com.access.accessonandroid.Network.NetworkAdapter;
import com.access.accessonandroid.Network.NetworkOperation.INetworkOperation;
import com.access.accessonandroid.Network.NetworkOperation.Request.GetIDRequest;
import com.access.accessonandroid.Network.NetworkOperation.Response.GetIDResponse;

import java.io.IOException;

/**
 * The EmployeeRecord class is responsible for handling the local representation of the user's
 * Employee Record, which contains relevant information about the user.
 *
 * Created by Tyler Collison on 10/24/2017.
 */
public class EmployeeRecord implements IHCEAccessCard {

    private String _username;
    private String _password;

    private String id = "access1234";

    //Stores the singleton instance
    private static EmployeeRecord instance = null;
    /**
     * Gets or creates the singleton instance
     * @return the singleton instance of this class
     */
    public static EmployeeRecord getInstance() {
        if(instance == null) {
            instance = new EmployeeRecord();
        }
        return instance;
    }

    @Override
    public String GetAccessID() {
        return id;
    }

    public void SetCredentials(String username, String password) {
        _username = username;
        _password = password;
    }

    public void RefreshAccessIDFromServer(String serverAddress) throws IOException {
        INetworkOperation getIdRequest = new GetIDRequest(_username, _password);
        INetworkAdapter adapter = new NetworkAdapter(serverAddress);
        adapter.postToServer(getIdRequest, new AbstractNetworkCallback<GetIDResponse>(new GetIDResponse()) {
            @Override
            protected void handleResponse(GetIDResponse response) {
                id = response.id;
                Log.v("EmployeeRecord", "EmployeeID: " + id);
            }
        });
    }
}
