package com.santander.pricing;

import org.springframework.stereotype.Component;

@Component
public class ErrorLogger {

    public void log(Exception e) {
        // Expect that errors would get logged somewhere that support team get notified in real system
        System.err.println(e.getMessage());
    }
}
