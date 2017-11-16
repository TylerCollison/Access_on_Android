package com.access.accessonandroid.Network.NetworkOperation.Response;

import com.access.accessonandroid.Network.NetworkOperation.INetworkOperation;
import com.google.gson.Gson;

/**
 * Created by Tyler Collison on 11/15/2017.
 */

public class ValidateUserResponse implements INetworkOperation {

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
