package com.santander.pricing;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultPriceMessageListener implements PriceMessageListener {

    private final PriceParser priceParser;

    private final PriceListener priceListener;

    private final ErrorLogger errorLogger;

    @Override
    public void onMessage(String message) {
        try {
            var price = priceParser.parse(message);
            priceListener.onPrice(price);
        } catch (PriceParserException | PricingException e) {
            errorLogger.log(e);
        }
    }
}
