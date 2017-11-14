package com.access.accessonandroid.Network.NetworkOperation.Response;

import com.access.accessonandroid.Network.NetworkOperation.INetworkOperation;
import com.access.accessonandroid.Network.NetworkOperation.Request.GetIDRequest;
import com.google.gson.Gson;

/**
 * Created by Tyler Collison on 11/12/2017.
 */

public class GetIDResponse implements INetworkOperation {

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
