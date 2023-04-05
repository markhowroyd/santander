package com.santander.pricing.domain;

public record CcyPair(String base, String term) {

    public static CcyPair fromSlashString(String value) {
        if (value.length() != 7 || value.charAt(3) != '/') {
            throw new IllegalArgumentException(
                    "Expected a string of 7 chars of the form XXX/YYY for ccy pair, but was " + value);
        }
        return new CcyPair(value.substring(0, 3), value.substring(4));
    }
}
