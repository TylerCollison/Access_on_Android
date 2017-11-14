package com.access.accessonandroid.Network;

import com.access.accessonandroid.Network.NetworkOperation.INetworkOperation;

/**
 * Created by Tyler Collison on 11/12/2017.
 */

public abstract class AbstractNetworkCallback<T extends INetworkOperation> implements INetworkCallback {

    private T _response;

    public AbstractNetworkCallback(T response) {
        _response = response;
    }

    public void execute(String jsonString) {
        _response.populateFromJsonString(jsonString);
        handleResponse(_response);
    }

    protected abstract void handleResponse(T response);
}
