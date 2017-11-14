package com.access.accessonandroid.Network.NetworkOperation;

/**
 * Created by Tyler Collison on 11/12/2017.
 */

public interface INetworkOperation {

    String toJsonString();
    void populateFromJsonString(String jsonString);

}
