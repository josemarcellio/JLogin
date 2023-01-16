package com.josemarcellio.jlogin.captcha;

import org.apache.commons.lang.RandomStringUtils;

public class Captcha {

    private final String type;
    private final int size;

    public Captcha(
            String type, int size) {
        this.type = type;
        this.size = size;
    }

    public String generate() {

        switch (type.toLowerCase()) {
            case "alpha":
                return RandomStringUtils.randomAlphabetic(size);
            case "num":
                return RandomStringUtils.randomNumeric(size);
            case "mix":
            default:
                return RandomStringUtils.randomAlphanumeric(size);
        }
    }
}