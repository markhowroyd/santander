package com.santander.pricing;

import com.santander.pricing.domain.CcyPair;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CcyPairServiceTest {

    private final CcyPairService ccyPairService = new CcyPairService();

    @Test
    void getPrecision_jpy_base() {
        assertThat(ccyPairService.getSpotPrecision(new CcyPair("JPY", "EUR"))).isEqualTo(2);
    }

    @Test
    void getPrecision_jpy_term() {
        assertThat(ccyPairService.getSpotPrecision(new CcyPair("EUR", "JPY"))).isEqualTo(2);
    }

    @Test
    void getPrecision_non_jpy() {
        assertThat(ccyPairService.getSpotPrecision(new CcyPair("EUR", "GBP"))).isEqualTo(4);
    }
}