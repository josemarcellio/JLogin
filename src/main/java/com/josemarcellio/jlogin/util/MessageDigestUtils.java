package com.josemarcellio.jlogin.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestUtils {

    public byte[] getSHA256Hash(
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

    public byte[] getMD5Hash(
            String input) {

        try {
            MessageDigest messageDigest = MessageDigest
                    .getInstance("MD5");

            messageDigest.update(
                    input.getBytes());

            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] getSHA512Hash(
            String input) {

        try {
            MessageDigest messageDigest = MessageDigest
                    .getInstance("SHA-512");

            messageDigest.update(
                    input.getBytes());

            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
