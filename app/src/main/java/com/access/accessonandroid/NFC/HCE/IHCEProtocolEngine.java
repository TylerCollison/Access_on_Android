package com.access.accessonandroid.NFC.HCE;

/**
 * The HCEProtocolEngine is an interface that defines the basic interaction with any HCE protocol.
 * In this way, the specifics of the HCE protocol are abstracted from the client.
 *
 * Created by Tyler Collison on 10/24/2017.
 */
public interface IHCEProtocolEngine {
    byte[] getResponse(byte[] request);
}
