package com.access.accessonandroid.NFC.HCE;

import java.io.IOException;

/**
 * @author Tyler Collison
 *
 * The HCEAccessCard interface defines a card that can be transmitted over NFC for access control
 * purposes. This card only represents the data for the NFC transaction, and is not responsible for
 * handling the NFC protocol.
 */
public interface IHCEAccessCard {
    /**
     * Gets the access ID corresponding to this HCE Access Card
     * @return The access ID corresponding to this card
     */
    String GetAccessID();
}
