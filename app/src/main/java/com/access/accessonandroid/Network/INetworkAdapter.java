package com.access.accessonandroid.Network;

import com.access.accessonandroid.Network.NetworkOperation.INetworkOperation;

import java.io.IOException;

/**
 * Created by Tyler Collison on 11/12/2017.
 */

public interface INetworkAdapter {

    void postToServer(INetworkOperation request, INetworkCallback responseCallback);

}
