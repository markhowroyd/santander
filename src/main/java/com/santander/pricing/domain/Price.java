package com.santander.pricing.domain;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Value
@Builder(toBuilder = true)
public class Price {

    String id;

    CcyPair ccyPair;

    BigDecimal bid;

    BigDecimal ask;

    ZonedDateTime timestamp;
}
