package com.santander.pricing;

import com.santander.pricing.domain.CcyPair;
import com.santander.pricing.domain.Price;

import java.util.Optional;

public interface PriceRepository {

    void insertPrice(Price price) throws PriceRepositoryException;

    Optional<Price> fetchPrice(CcyPair ccyPair);
}
