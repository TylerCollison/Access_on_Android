package com.access.accessonandroid.Network.NetworkOperation.Request;

import com.access.accessonandroid.Network.NetworkOperation.INetworkOperation;
import com.google.gson.Gson;

/**
 * @author Tyler Collison
 *
 * This class represents a JSON authentication request to be sent to the server for verification
 */
public class AuthenticatedRequest implements INetworkOperation {

    //Store the username and password
    public String username;
    public String password;

    public AuthenticatedRequest(String _username, String _password) {
        username = _username;
        password = _password;
    }

    @Override
    public String toJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public void populateFromJsonString(String jsonString) {
        Gson gson = new Gson();
        AuthenticatedRequest req = gson.fromJson(jsonString, this.getClass());
        username = req.username;
        password = req.password;
    }
}
