package com.access.accessonandroid.Network.NetworkOperation.Response;

import com.access.accessonandroid.Network.NetworkOperation.INetworkOperation;
import com.google.gson.Gson;

/**
 * @author Tyler Collison
 *
 * This class represents a JSON response from the server that contains an access token
 */
public class GetIDResponse implements INetworkOperation {

    //Store the access token
    public String id;

    @Override
    public String toJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public void populateFromJsonString(String jsonString) {
        Gson gson = new Gson();
        GetIDResponse req = gson.fromJson(jsonString, this.getClass());
        id = req.id;
    }
}
