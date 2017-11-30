package com.access.accessonandroid.NFC.HCE;

/**
 * @author Tyler Collison
 *
 * The HCEProtocolEngine is an interface that defines the basic interaction with any HCE protocol.
 * In this way, the specifics of the HCE protocol are abstracted from the client.
 */
public interface IHCEProtocolEngine {
    /**
     * Gets the response to the given input request according to the protocol
     * @param request The input request to formulate the response around
     * @return The appropriate response byte array to the request
     */
    byte[] getResponse(byte[] request);

    /**
     * Gets the byte array code corresponding to the given command for this protocol
     * @param command The command to generate code for
     * @return The code corresponding to the command
     */
    byte[] getCommandCode(HCECommand command);
}
