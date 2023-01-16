package com.josemarcellio.jlogin.encryption;

import com.josemarcellio.jlogin.util.MessageDigestUtils;

public class Encryption {

    private final String method;

    public Encryption(
            String method) {
        this.method = method;
    }

    public byte[] encryptPassword(
            String password) {

        switch (method.toLowerCase()) {
            case "sha512":
                return new MessageDigestUtils().getSHA512Hash(password);
            case "md5":
                return new MessageDigestUtils().getMD5Hash(password);
            case "sha256":
            default:
                return new MessageDigestUtils().getSHA256Hash(password);
        }
    }
}