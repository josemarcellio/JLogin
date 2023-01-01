package com.josemarcellio.jlogin.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestUtils {

    public static byte[] getSHA256Hash(
            String input) {

        try {
            MessageDigest messageDigest = MessageDigest
                    .getInstance("SHA-256");

            messageDigest.update(
                    input.getBytes());

            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
