package com.access.accessonandroid.NFC.HCE;

import java.util.Arrays;

/**
 * The HCEAccessProtocolEngine class implements the HCEProtocolEngine interface and is responsible
 * for handling the specifics of the HCE Access Control protocol. In other words, it handles the
 * interface between an HCEAccessCard and the NFC communication client.
 *
 * Created by Tyler Collison on 10/24/2017.
 */
public class HCEAccessProtocolEngine implements IHCEProtocolEngine {

    // Stores the user's access card to be interpreted
    private IHCEAccessCard accessCard;
    // AID for access control
    private static final String ACCESS_CONTROL_AID = "F222222222";
    // ISO-DEP command HEADER for selecting an AID.
    // Format: [Class | Instruction | Parameter 1 | Parameter 2]
    private static final String SELECT_APDU_HEADER = "00A40400";
    // "OK" status word sent in response to SELECT AID command (0x9000)
    private static final byte[] SELECT_OK_SW = HexStringToByteArray("9000");
    // "UNKNOWN" status word sent in response to invalid APDU command (0x0000)
    private static final byte[] UNKNOWN_CMD_SW = HexStringToByteArray("0000");
    // Create the APDU Select command by merging the select header and AID
    private static final byte[] SELECT_APDU = BuildSelectApdu(ACCESS_CONTROL_AID);

    public HCEAccessProtocolEngine(IHCEAccessCard card) {
        accessCard = card;
    }

    @Override
    public byte[] getResponse(byte[] request) {
        byte[] response = UNKNOWN_CMD_SW;
        if (isSelectAPDU(request)) {
            response = createIDResponse();
        }
        return response;
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
        return ConcatArrays(SELECT_OK_SW, accessCard.getAccessID().getBytes());
    }

    /**
     * Build APDU for SELECT AID command. This command indicates which service a reader is
     * interested in communicating with. See ISO 7816-4.
     *
     * @param aid Application ID (AID) to select
     * @return APDU for SELECT AID command
     */
    private static byte[] BuildSelectApdu(String aid) {
        // Format: [CLASS | INSTRUCTION | PARAMETER 1 | PARAMETER 2 | LENGTH | DATA]
        return HexStringToByteArray(SELECT_APDU_HEADER + String.format("%02X",
                aid.length() / 2) + aid);
    }

    /**
     * Utility method to convert a hexadecimal string to a byte string.
     *
     * <p>Behavior with input strings containing non-hexadecimal characters is undefined.
     *
     * @param s String containing hexadecimal characters to convert
     * @return Byte array generated from input
     * @throws java.lang.IllegalArgumentException if input length is incorrect
     */
    private static byte[] HexStringToByteArray(String s) throws IllegalArgumentException {
        int len = s.length();
        if (len % 2 == 1) {
            throw new IllegalArgumentException("Hex string must have even number of characters");
        }
        byte[] data = new byte[len / 2]; // Allocate 1 byte per 2 hex characters
        for (int i = 0; i < len; i += 2) {
            // Convert each character into a integer (base-16), then bit-shift into place
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    /**
     * Utility method to concatenate two byte arrays.
     * @param first First array
     * @param rest Any remaining arrays
     * @return Concatenated copy of input arrays
     */
    public static byte[] ConcatArrays(byte[] first, byte[]... rest) {
        int totalLength = first.length;
        for (byte[] array : rest) {
            totalLength += array.length;
        }
        byte[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (byte[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }
}
