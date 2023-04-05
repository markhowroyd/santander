package com.santander.pricing;

import com.santander.pricing.domain.Price;

public interface PriceListener {

    void onPrice(Price price) throws PricingException;
}
