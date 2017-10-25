package com.access.accessonandroid.NFC.HCE;

/**
 * The HCEAccessCard interface defines a card that can be transmitted over NFC for access control
 * purposes. This card only represents the data for the NFC transaction, and is not responsible for
 * handling the NFC protocol.
 * Created by Tyler Collison on 10/24/2017.
 */
public interface IHCEAccessCard {
    String getAccessID();
}
