package com.access.accessonandroid.NFC.HCE;

import android.util.Log;

import java.util.Arrays;

/**
 * @author Tyler Collison
 *
 * The HCEAccessProtocolEngine class implements the HCEProtocolEngine interface and is responsible
 * for handling the specifics of the HCE Access Control protocol. In other words, it handles the
 * interface between an HCEAccessCard and the NFC communication client.
 */
public class HCEAccessProtocolEngine implements IHCEProtocolEngine {

    // Stores the user's access card to be interpreted
    private IHCEAccessCard accessCard;
    // AID for access control
    private static final String ACCESS_CONTROL_AID = "F222222222";
    // HEADER for selecting an AID.
    private static final String SELECT_APDU_HEADER = "00A40400";
    // "OK" status word sent in response to SELECT AID command (0x9000)
    private static final byte[] SELECT_OK_SW = toByteArray("9000");
    // "UNKNOWN" status word (0x0000)
    private static final byte[] UNKNOWN_CMD_SW = toByteArray("0000");
    // "WAIT" status word (0x5000)
    private static final byte[] WAIT_CMD_SW = toByteArray("5000");
    // Create the APDU Select command by merging the select header and AID
    private static final byte[] SELECT_APDU = createSelectAPDU(ACCESS_CONTROL_AID);

    public HCEAccessProtocolEngine(IHCEAccessCard card) {
        accessCard = card;
    }

    @Override
    public byte[] getResponse(byte[] request) {
        //Set the default response
        byte[] response = UNKNOWN_CMD_SW;
        //Determine whether the request is the SELECT APDU
        if (isSelectAPDU(request)) {
            Log.v("NFC", "Received Select ADPU");
            //Generate the SELECT APDU response
            response = createIDResponse();
        }
        return response;
    }

    @Override
    public byte[] getCommandCode(HCECommand command) {
        byte[] result = new byte[0];

        //Choose the appropriate command code
        switch (command) {
            case Wait:
                result = WAIT_CMD_SW;
                break;
        }

        return result;
    }

    /**
     * Determines whether the given APDU Command is the Select APDU Command for access control
     * @param apdu The APDU Command to be checked
     * @return Whether the APDU Command is the Select APDU Command for access control
     */
    private boolean isSelectAPDU(byte[] apdu) {
        return Arrays.equals(SELECT_APDU, apdu);
    }

    /**
     * Creates an NFC response that specifies the "OK" message followed by the user's access ID
     * @return Response as a byte array
     */
    private byte[] createIDResponse() {
        return mergeArrays(accessCard.GetAccessID().getBytes(), SELECT_OK_SW);
    }

    /**
     * Build APDU for SELECT AID command for this application
     * @param aid The application ID
     * @return The APDU SELECT AID command
     */
    private static byte[] createSelectAPDU(String aid) {
        return toByteArray(SELECT_APDU_HEADER + String.format("%02X",
                aid.length() / 2) + aid);
    }

    /**
     * Converts a hex string to a byte array.
     * @param s The hex string to be converted
     * @return The byte array representation of the hex string
     */
    private static byte[] toByteArray(String s) {
        //Get the length of the hex string
        int length = s.length();
        //Allocate the byte array
        byte[] bytes = new byte[length / 2];
        //Convert each character of the hex string into an integer
        for (int i = 0; i < length; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return bytes;
    }

    /**
     * Concatenates the given byte arrays into a single array.
     * @param base First array
     * @param additional Additional arrays
     * @return Merged copy of arrays
     */
    private static byte[] mergeArrays(byte[] base, byte[]... additional) {
        //Get the length of the base array
        int arrayLength = base.length;
        //Add the length of the remaining arrays
        for (byte[] array : additional) {
            arrayLength += array.length;
        }
        //Create a new array and place the members of each array into it in order
        byte[] result = Arrays.copyOf(base, arrayLength);
        int offset = base.length;
        for (byte[] array : additional) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }
}
