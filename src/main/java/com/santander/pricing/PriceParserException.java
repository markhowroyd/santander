package com.santander.pricing;

public class PriceParserException extends Exception {

    public PriceParserException(String message) {
        super(message);
    }

    public PriceParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
