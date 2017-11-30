package com.access.accessonandroid.Network.NetworkOperation.Response;

import com.access.accessonandroid.Network.NetworkOperation.INetworkOperation;
import com.google.gson.Gson;

/**
 * @author Tyler Collison
 *
 * This class represents a JSON response from the server that indicates whether the supplied
 * username and password were valid.
 */
public class ValidateUserResponse implements INetworkOperation {

    //Store whether the username and password were valid
    public boolean username_is_valid;
    public boolean password_is_valid;

    @Override
    public String toJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public void populateFromJsonString(String jsonString) {
        Gson gson = new Gson();
        ValidateUserResponse req = gson.fromJson(jsonString, this.getClass());
        username_is_valid = req.username_is_valid;
        password_is_valid = req.password_is_valid;
    }
}
