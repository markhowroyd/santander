package com.santander.pricing;

import com.santander.pricing.domain.Price;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PriceProcessor implements PriceListener {

    private final Function<Price, Price> commissionFunction;

    private final PriceRepository priceRepository;

    public void onPrice(Price price) throws PricingException {
        var clientPrice = commissionFunction.apply(price);
        try {
            priceRepository.insertPrice(clientPrice);
        } catch (PriceRepositoryException e) {
            throw new PricingException("Error encountered whilst storing price " + clientPrice.getId(), e);
        }
    }
}
