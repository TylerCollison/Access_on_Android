package com.access.accessonandroid.Network;

/**
 * @author Tyler Collison
 *
 * This defines the interface for all network operation callback objects.
 */
public interface INetworkCallback {

    /**
     * Executes this callback
     * @param jsonString The JSON string representation of the response
     */
    void execute(String jsonString);

}
