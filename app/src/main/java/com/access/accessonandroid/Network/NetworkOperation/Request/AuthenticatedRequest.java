package com.access.accessonandroid.Network.NetworkOperation.Request;

import com.access.accessonandroid.Network.NetworkOperation.INetworkOperation;
import com.google.gson.Gson;

/**
 * Created by Tyler Collison on 11/12/2017.
 */

public class AuthenticatedRequest implements INetworkOperation {

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
