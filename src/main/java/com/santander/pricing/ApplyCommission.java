package com.santander.pricing;

import com.santander.pricing.domain.Price;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;

public class ApplyCommission implements Function<Price, Price> {

    private CcyPairService ccyPairService;

    private final BigDecimal bidCommissionMultiplier;
    private final BigDecimal askCommissionMultiplier;

    public ApplyCommission(CcyPairService ccyPairService, BigDecimal commissionAsDecimal) {
        this.ccyPairService = ccyPairService;
        bidCommissionMultiplier = BigDecimal.ONE.subtract(commissionAsDecimal);
        askCommissionMultiplier = BigDecimal.ONE.add(commissionAsDecimal);
    }

    @Override
    public Price apply(Price price) {
        var precision = ccyPairService.getSpotPrecision(price.getCcyPair());
        var bid = price.getBid().multiply(bidCommissionMultiplier).setScale(precision, RoundingMode.HALF_UP);
        var ask = price.getAsk().multiply(askCommissionMultiplier).setScale(precision, RoundingMode.HALF_UP);
        return price.toBuilder()
                .bid(bid)
                .ask(ask).build();
    }
}
