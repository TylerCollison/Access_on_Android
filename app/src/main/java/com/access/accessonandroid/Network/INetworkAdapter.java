package com.access.accessonandroid.Network;

import com.access.accessonandroid.Network.NetworkOperation.INetworkOperation;

import java.io.IOException;

/**
 * @author Tyler Collison
 *
 * This defines the interface for the network adapter that can be used to make web requests.
 */
public interface INetworkAdapter {

    /**
     * Sends the specified request to the server and executes the specified callback at the
     * conclusion of the network operation
     * @param request The request message to be sent to the server
     * @param responseCallback The response callback
     */
    void postToServer(INetworkOperation request, INetworkCallback responseCallback);

}
