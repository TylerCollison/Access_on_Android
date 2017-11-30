package com.access.accessonandroid.Network;

import com.access.accessonandroid.Network.NetworkOperation.INetworkOperation;

/**
 * @author Tyler Collison
 *
 * This abstract class defines the basis for a callback object that is called at the conclusion
 * of a network request.
 */
public abstract class AbstractNetworkCallback<T extends INetworkOperation> implements INetworkCallback {

    //Store the web response
    private T _response;

    public AbstractNetworkCallback(T response) {
        _response = response;
    }

    public void execute(String jsonString) {
        //Parse the response into an object
        _response.populateFromJsonString(jsonString);
        //Handle the response
        handleResponse(_response);
    }

    /**
     * Manipulates the network response
     * @param response The network response
     */
    protected abstract void handleResponse(T response);
}
