package com.access.accessonandroid.Network.NetworkOperation;

/**
 * @author Tyler Collison
 *
 * This defines an interface for all web request messages (both requests and responses)
 */
public interface INetworkOperation {

    /**
     * Converts this object into a JSON string
     * @return The JSON string representation of this object
     */
    String toJsonString();

    /**
     * Populates this object's properties from its given JSON string representation
     * @param jsonString A JSON string representation of this object
     */
    void populateFromJsonString(String jsonString);
}
