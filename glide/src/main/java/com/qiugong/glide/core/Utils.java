package com.qiugong.glide.core;

/**
 * @author qzx 20/2/20.
 */
public class Utils {

    private static final char[] HEX_CHAR_ARRAY = "0123456789abcdef".toCharArray();
    private static final char[] SHA_256_CHARS = new char[64];

    public static String sha256BytesToHex(byte[] bytes) {
        synchronized (SHA_256_CHARS) {
            return bytesToHex(bytes, SHA_256_CHARS);
        }
    }

    private static String bytesToHex(byte[] bytes, char[] hexChars) {
        int value;
        for (int j = 0; j < bytes.length; j++) {
            value = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_CHAR_ARRAY[value >>> 4];
            hexChars[j * 2 + 1] = HEX_CHAR_ARRAY[value & 0x0F];
        }
        return new String(hexChars);
    }
}
