package com.santander.pricing;

import com.santander.pricing.domain.CcyPair;
import com.santander.pricing.domain.Price;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryPriceRepository implements PriceRepository {

    private final Map<CcyPair, Price> pricesByInstrument = new ConcurrentHashMap<>();

    @Override
    public void insertPrice(Price price) {
        pricesByInstrument.put(price.getCcyPair(), price);
    }

    @Override
    public Optional<Price> fetchPrice(CcyPair ccyPair) {
        return Optional.ofNullable(pricesByInstrument.get(ccyPair));
    }
}
