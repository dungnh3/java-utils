package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

    public static final String SHA1 = "SHA1";
    public static final String SHA256 = "SHA-256";

    public static String hashSHA256(String input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(SHA256);
            //Todo
            byte[] shaByteArr = messageDigest.digest(StringUtils.encodeUTF8(input));

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < shaByteArr.length; i++) {
                stringBuilder.append(Integer.toHexString(0xFF & shaByteArr[i]));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String hashSHA1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

}
