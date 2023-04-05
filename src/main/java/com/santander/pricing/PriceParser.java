package com.santander.pricing;

import com.santander.pricing.domain.Price;

public interface PriceParser {
    Price parse(String priceMessage) throws PriceParserException;
}
