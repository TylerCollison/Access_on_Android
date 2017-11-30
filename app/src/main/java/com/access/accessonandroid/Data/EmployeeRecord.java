package com.access.accessonandroid.Data;

import android.util.Log;

import com.access.accessonandroid.NFC.HCE.IHCEAccessCard;
import com.access.accessonandroid.UserRegistration.UserAccess;
import com.access.accessonandroid.Network.AbstractNetworkCallback;
import com.access.accessonandroid.Network.INetworkAdapter;
import com.access.accessonandroid.Network.NetworkAdapter;
import com.access.accessonandroid.Network.NetworkOperation.INetworkOperation;
import com.access.accessonandroid.Network.NetworkOperation.Request.AuthenticatedRequest;
import com.access.accessonandroid.Network.NetworkOperation.Response.GetIDResponse;

import java.io.IOException;

/**
 * @author Tyler Collison
 * @author Megan Goins
 *
 * The EmployeeRecord class is responsible for handling the local representation of the user's
 * Employee Record, which contains relevant information about the user.
 */
public class EmployeeRecord implements IHCEAccessCard, UserAccess{

    //Store the username and password
    private String _username;
    private String _password;

    private String id = "";

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

    /**
     * Determines whether the record credentials have been set
     * @return Whether the credentials have been set
     */
    public boolean HasCredentials() {
        return _username != null && _password != null;
    }

    /**
     * Refreshes the access token from the server using the stored credentials
     * @param serverAddress The address of the application server
     * @throws IOException
     */
    public void RefreshAccessIDFromServer(String serverAddress) throws IOException {
        //Create a new authentication request
        INetworkOperation getIdRequest = new AuthenticatedRequest(_username, _password);
        //Create a new network adapter
        INetworkAdapter adapter = new NetworkAdapter(serverAddress);
        //Send web request for the access token
        adapter.postToServer(getIdRequest, new AbstractNetworkCallback<GetIDResponse>(new GetIDResponse()) {
            @Override
            protected void handleResponse(GetIDResponse response) {
                //Set the access token
                id = response.id;
                Log.v("EmployeeRecord", "EmployeeID: " + id);
            }
        });
    }

    @Override
    public Boolean isUser(String username){
        return true;
    }

    @Override
    public void updateUserPassword(String password){ }

    @Override
    public String getUserPassword(){
        return "password1234";
    }
}
